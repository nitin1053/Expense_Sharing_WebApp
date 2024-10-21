package com.example.expense_sharing_webapp.models;



public class SplitDTO {

    private Long userId;
    private String splitType;
    private Double amount;

    public SplitDTO(Long userId, String splitType, Double amount) {
        this.userId = userId;
        this.splitType = splitType;
        this.amount = amount;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSplitType() {
        return splitType;
    }

    public void setSplitType(String splitType) {
        this.splitType = splitType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

