
package com.example.instructions.util;

import com.example.instructions.model.CanonicalTrade;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TradeTransformerTest {

    @Test
    void testNormalize() {
        CanonicalTrade trade = new CanonicalTrade();
        trade.setAccountNumber("123456789");
        trade.setSecurityId("abc123");
        trade.setTradeType("buy");
        trade.setTradeDate("2025-11-26");
        trade.setAmount(1000.50);

        CanonicalTrade normalized = TradeTransformer.normalize(trade);

        assertEquals("****6789", normalized.getAccountNumber());
        assertEquals("ABC123", normalized.getSecurityId());
        assertEquals("BUY", normalized.getTradeType());
    }

    @Test
    void testToPlatformJson() {
        CanonicalTrade trade = new CanonicalTrade();
        trade.setAccountNumber("****6789");
        trade.setSecurityId("ABC123");
        trade.setTradeType("BUY");
        trade.setTradeDate("2025-11-26");
        trade.setAmount(1000.50);

        var platformTrade = TradeTransformer.toPlatformJson(trade);

        assertEquals("ABC123", platformTrade.getSecurity());
        assertEquals("****6789", platformTrade.getAccount());
        assertEquals("BUY", platformTrade.getType());
    }


    @Test
    void testNormalizeWithNullValues() {
        CanonicalTrade trade = new CanonicalTrade();
        trade.setAccountNumber(null);
        trade.setSecurityId(null);
        trade.setTradeType(null);

        CanonicalTrade normalized = TradeTransformer.normalize(trade);

        assertNull(normalized.getAccountNumber());
        assertNull(normalized.getSecurityId());
        assertEquals("UNKNOWN", normalized.getTradeType());
    }
}
