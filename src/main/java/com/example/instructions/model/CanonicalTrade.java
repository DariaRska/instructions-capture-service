package com.example.instructions.model;

import lombok.Data;

@Data
public class CanonicalTrade {
    private String accountNumber;
    private String securityId;
    private String tradeType;
    private String tradeDate;
    private double amount;
}
