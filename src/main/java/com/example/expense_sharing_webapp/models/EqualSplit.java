package com.example.expense_sharing_webapp.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
@Entity
public class EqualSplit extends Split {

    // No additional fields, use Split amount
}

