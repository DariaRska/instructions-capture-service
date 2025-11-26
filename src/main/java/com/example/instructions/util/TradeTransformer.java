
package com.example.instructions.util;

import com.example.instructions.model.CanonicalTrade;
import com.example.instructions.model.PlatformTrade;

public class TradeTransformer {

    /**
     * Normalize CanonicalTrade:
     * - Mask account number (keep last 4 digits)
     * - Uppercase securityId
     * - Normalize tradeType to BUY/SELL/UNKNOWN
     */
    public static CanonicalTrade normalize(CanonicalTrade trade) {
        CanonicalTrade normalized = new CanonicalTrade();

        // Mask account number
        String acc = trade.getAccountNumber();
        if (acc != null && acc.length() > 4) {
            acc = "****" + acc.substring(acc.length() - 4);
        }
        normalized.setAccountNumber(acc);

        // Uppercase securityId
        String sec = trade.getSecurityId();
        normalized.setSecurityId(sec == null ? null : sec.toUpperCase());

        // Normalize tradeType
        String type = trade.getTradeType();
        type = (type == null) ? "UNKNOWN" : type.trim().toUpperCase();
        if (!type.equals("BUY") && !type.equals("SELL")) {
            type = "UNKNOWN";
        }
        normalized.setTradeType(type);

        // Copy remaining fields
        normalized.setTradeDate(trade.getTradeDate());
        normalized.setAmount(trade.getAmount());

        return normalized;
    }

    /**
     * Convert CanonicalTrade to PlatformTrade JSON structure
     */

    public static PlatformTrade toPlatformJson(CanonicalTrade trade) {
        PlatformTrade pt = new PlatformTrade();
        pt.setPlatform_id("ABC123"); // Hardcoded or from config
        pt.setAccount(trade.getAccountNumber());
        pt.setSecurity(trade.getSecurityId());
        pt.setType(trade.getTradeType());
        pt.setDate(trade.getTradeDate());
        pt.setAmount(trade.getAmount());
        return pt;
    }
}
