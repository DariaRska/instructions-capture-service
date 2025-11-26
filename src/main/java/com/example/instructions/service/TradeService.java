
package com.example.instructions.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.instructions.model.CanonicalTrade;
import com.example.instructions.util.TradeTransformer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class TradeService {

    private final KafkaPublisher publisher;

    public TradeService(KafkaPublisher publisher) {
        this.publisher = publisher;
    }


    public String processFile(MultipartFile file) {
        try {

            String filename = file.getOriginalFilename();
            if (filename == null) {
                throw new IllegalArgumentException("File name is missing");
            }

            List<CanonicalTrade> trades;

            if (filename.endsWith(".csv")) {
                trades = parseCsv(file);
            } else if (filename.endsWith(".json")) {
                trades = parseJson(file);
            } else {
                throw new IllegalArgumentException("Unsupported file type");
            }

            for (CanonicalTrade trade : trades) {
                CanonicalTrade normalized = TradeTransformer.normalize(trade);
                String json = toJson(TradeTransformer.toPlatformJson(normalized));
                publisher.send(json);
            }

            return "File processed successfully!";
        } catch (Exception e) {
            throw new RuntimeException("Failed to process file", e);
        }
    }

    private List<CanonicalTrade> parseCsv(MultipartFile file) throws IOException {
        List<CanonicalTrade> trades = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                CanonicalTrade trade = new CanonicalTrade();
                trade.setAccountNumber(parts[0]);
                trade.setSecurityId(parts[1]);
                trade.setTradeType(parts[2]);
                trade.setTradeDate(parts[3]);
                trade.setAmount(Double.parseDouble(parts[4]));
                trades.add(trade);
            }
        }
        return trades;
    }

    private List<CanonicalTrade> parseJson(MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file.getInputStream(), new TypeReference<>() {
        });
    }

    private String toJson(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert to JSON", e);
        }
    }

}
