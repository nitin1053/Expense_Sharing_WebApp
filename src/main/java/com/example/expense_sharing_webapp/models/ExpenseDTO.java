package com.example.expense_sharing_webapp.models;



import java.util.List;

public class ExpenseDTO {

    private Long id;
    private String description;
    private Double totalAmount;
    private List<SplitDTO> splits;

    public ExpenseDTO(Long id, String description, Double totalAmount, List<SplitDTO> splits) {
        this.id = id;
        this.description = description;
        this.totalAmount = totalAmount;
        this.splits = splits;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<SplitDTO> getSplits() {
        return splits;
    }

    public void setSplits(List<SplitDTO> splits) {
        this.splits = splits;
    }
}

