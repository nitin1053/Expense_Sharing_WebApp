package com.example.expense_sharing_webapp.models;

import jakarta.persistence.Entity;

@Entity
public class PercentageSplit extends Split {
    private Double percentage;

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
}
