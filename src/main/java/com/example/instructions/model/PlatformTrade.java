
package com.example.instructions.model;

import lombok.Data;

@Data
public class PlatformTrade {
    private String platform_id;
    private String account;
    private String security;
    private String type;
    private String date;
    private double amount;
}
