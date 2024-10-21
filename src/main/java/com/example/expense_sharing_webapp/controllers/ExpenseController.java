package com.example.expense_sharing_webapp.controllers;
import com.example.expense_sharing_webapp.models.ExpenseDTO;
import com.example.expense_sharing_webapp.models.ExpenseRequest;
import com.example.expense_sharing_webapp.services.ExpenseService;
import jakarta.annotation.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.expense_sharing_webapp.models.Expense;

import java.io.IOException;


@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseDTO> addExpense(@RequestBody ExpenseRequest expenseRequest) {
        Expense savedExpense = expenseService.addExpense(expenseRequest);  // Convert DTO to Entity inside the service
        ExpenseDTO expenseDTO = expenseService.convertToDTO(savedExpense); // Convert Entity back to DTO for response
        return ResponseEntity.ok(expenseDTO);
    }

    @GetMapping("/{userId}/balance-sheet")
    public ResponseEntity<ByteArrayResource> downloadBalanceSheet(
            @PathVariable Long userId,
            @RequestParam String format) throws IOException {

        ByteArrayResource resource;
        String filename;
        String contentType;

        if ("pdf".equalsIgnoreCase(format)) {
            resource = expenseService.generateBalanceSheetPDF(userId);
            filename = "balance_sheet.pdf";
            contentType = "application/pdf";
        } else if ("csv".equalsIgnoreCase(format)) {
            resource = expenseService.generateBalanceSheetCSV(userId);
            filename = "balance_sheet.csv";
            contentType = "text/csv";
        } else {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .body(resource);
    }
    @GetMapping("/expenses")
    public ResponseEntity<Page<Expense>> getExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Expense> expenses = expenseService.getAllExpenses(paging);
        return ResponseEntity.ok(expenses);
    }

}


