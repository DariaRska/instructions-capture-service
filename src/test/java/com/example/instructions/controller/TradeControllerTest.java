
package com.example.instructions.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void testUploadCsvFile() throws Exception {
        String csv = "accountNumber,securityId,tradeType,tradeDate,amount\n" +
                "123456789,abc123,buy,2025-11-26,1000.50";

        MockMultipartFile file = new MockMultipartFile("file", "trades.csv", "text/csv", csv.getBytes());

        mockMvc.perform(multipart("/api/trades/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("File processed successfully!"));
    }
}
