package com.example.expense_sharing_webapp.services;

import com.example.expense_sharing_webapp.models.*;
import com.example.expense_sharing_webapp.repositories.ExpenseRepository;
import com.example.expense_sharing_webapp.repositories.UserRepository;
import jakarta.annotation.Resource;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    public Expense addExpense(ExpenseRequest expenseRequest) {
        Expense expense = new Expense();
        expense.setDescription(expenseRequest.getDescription());
        expense.setTotalAmount(expenseRequest.getTotalAmount());

        List<User> participants = userRepository.findAllById(expenseRequest.getParticipantIds());
        expense.setParticipants(participants);

        List<Split> splits = createSplits(expenseRequest, expense);
        expense.setSplits(splits);

        return expenseRepository.save(expense);
    }

    private List<Split> createSplits(ExpenseRequest expenseRequest, Expense expense) {
        List<SplitRequest> splitRequests = expenseRequest.getSplits();

        // Ensure percentage split totals 100%
        if (splitRequests.stream().anyMatch(sr -> "PERCENTAGE".equals(sr.getSplitType()))) {
            double totalPercentage = splitRequests.stream()
                    .filter(sr -> "PERCENTAGE".equals(sr.getSplitType()))
                    .mapToDouble(SplitRequest::getPercentage)
                    .sum();

            if (totalPercentage != 100) {
                throw new IllegalArgumentException("Total percentage must add up to 100%");
            }
        }

        return splitRequests.stream().map(sr -> {
            Split split;
            User participant = userRepository.findById(sr.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            switch (sr.getSplitType().toUpperCase()) {
                case "EXACT":
                    split = new ExactSplit();
                    split.setAmount(sr.getAmount());
                    break;

                case "PERCENTAGE":
                    PercentageSplit percentageSplit = new PercentageSplit();
                    percentageSplit.setPercentage(sr.getPercentage());
                    percentageSplit.setAmount((sr.getPercentage() / 100) * expense.getTotalAmount());
                    split = percentageSplit;
                    break;

                case "EQUAL":
                default:
                    split = new EqualSplit();
                    split.setAmount(expense.getTotalAmount() / expenseRequest.getParticipantIds().size());
                    break;
            }

            split.setUser(participant);
            split.setExpense(expense);
            return split;
        }).collect(Collectors.toList());
    }


    public BalanceSheet getUserBalanceSheet(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Expense> allExpenses = expenseRepository.findAll();

        // Individual and overall balances
        Map<Long, Double> userBalance = calculateUserBalance(allExpenses, user);

        return new BalanceSheet(user, allExpenses, userBalance);
    }

    private Map<Long, Double> calculateUserBalance(List<Expense> expenses, User user) {
        return expenses.stream()
                .filter(expense -> expense.getParticipants().contains(user))
                .collect(Collectors.toMap(
                        expense -> expense.getId(),
                        expense -> expense.getSplits().stream()
                                .filter(split -> split.getUser().equals(user))
                                .mapToDouble(Split::getAmount).sum()
                ));
    }

    public ByteArrayResource generateBalanceSheetPDF(Long userId) throws IOException {
        BalanceSheet balanceSheet = getUserBalanceSheet(userId);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
            contentStream.newLineAtOffset(25, 750);
            contentStream.showText("Balance Sheet for " + balanceSheet.getUser().getName());
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(25, 700);
            contentStream.showText("Individual Expenses:");
            contentStream.endText();

            int yPosition = 680;

            for (Expense expense : balanceSheet.getExpenses()) {
                contentStream.beginText();
                contentStream.newLineAtOffset(25, yPosition);
                contentStream.showText("Expense: " + expense.getDescription() + " - Amount: " + balanceSheet.getUserBalance().get(expense.getId()));
                contentStream.endText();
                yPosition -= 20;
            }

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(25, yPosition - 20);
            contentStream.showText("Overall Balance: " + balanceSheet.getTotalBalance());
            contentStream.endText();

            contentStream.close();
            document.save(outputStream);
        }

        return new ByteArrayResource(outputStream.toByteArray());
    }

    public ByteArrayResource generateBalanceSheetCSV(Long userId) throws IOException {
        BalanceSheet balanceSheet = getUserBalanceSheet(userId);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        StringBuilder csvData = new StringBuilder();

        csvData.append("Description,Amount\n");
        for (Expense expense : balanceSheet.getExpenses()) {
            csvData.append(expense.getDescription()).append(",")
                    .append(balanceSheet.getUserBalance().get(expense.getId())).append("\n");
        }
        csvData.append("Overall Balance,").append(balanceSheet.getTotalBalance()).append("\n");

        outputStream.write(csvData.toString().getBytes());
        return new ByteArrayResource(outputStream.toByteArray());
    }
    public ExpenseDTO convertToDTO(Expense expense) {
        List<SplitDTO> splitDTOs = expense.getSplits().stream()
                .map(this::convertSplitToDTO)
                .collect(Collectors.toList());

        return new ExpenseDTO(
                expense.getId(),
                expense.getDescription(),
                expense.getTotalAmount(),
                splitDTOs
        );
    }

    private SplitDTO convertSplitToDTO(Split split) {
        return new SplitDTO(
                split.getUser().getId(),
                split.getClass().getSimpleName(), // Or however you're representing the split type
                split.getAmount()
        );
    }
    public Page<Expense> getAllExpenses(Pageable pageable) {
        return expenseRepository.findAll(pageable);
    }

}