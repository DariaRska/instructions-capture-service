
package com.example.instructions.service;

import com.example.instructions.model.CanonicalTrade;
import com.example.instructions.util.TradeTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    private KafkaPublisher publisher;

    @InjectMocks
    private TradeService tradeService;

    @Test
    void testProcessCsvFile() throws Exception {
        String csv = "accountNumber,securityId,tradeType,tradeDate,amount\n" +
                "123456789,abc123,buy,2025-11-26,1000.50";

        MockMultipartFile file = new MockMultipartFile(
                "file", "trades.csv", "text/csv", csv.getBytes(StandardCharsets.UTF_8));

        String result = tradeService.processFile(file);

        assertEquals("File processed successfully!", result);
        verify(publisher, times(1)).send(anyString());
    }

    @Test
    void testProcessJsonFile() throws Exception {
        String json = "[{\"accountNumber\":\"123456789\",\"securityId\":\"abc123\",\"tradeType\":\"buy\",\"tradeDate\":\"2025-11-26\",\"amount\":1000.50}]";

        MockMultipartFile file = new MockMultipartFile(
                "file", "trades.json", "application/json", json.getBytes(StandardCharsets.UTF_8));

        String result = tradeService.processFile(file);

        assertEquals("File processed successfully!", result);
        verify(publisher, times(1)).send(anyString());
    }
}
