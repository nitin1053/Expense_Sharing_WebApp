package com.example.expense_sharing_webapp.models;

public class SplitRequest {
    private Long userId; // Participant user ID
    private String splitType; // EQUAL, EXACT, or PERCENTAGE
    private Double amount; // For EXACT split
    private Double percentage; //



    public SplitRequest(Long userId, String splitType, Double amount, Double percentage) {
        this.userId = userId;
        this.splitType = splitType;
        this.amount = amount;
        this.percentage = percentage;
    }

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

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
}
