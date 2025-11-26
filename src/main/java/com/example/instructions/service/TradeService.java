
package com.example.instructions.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.instructions.model.CanonicalTrade;
import com.example.instructions.util.TradeTransformer;
import java.util.List;

@Service
public class TradeService {

    private final KafkaPublisher publisher;

    public TradeService(KafkaPublisher publisher) {
        this.publisher = publisher;
    }

    public String processFile(MultipartFile file) {
        // Parse file (CSV or JSON)
        List<CanonicalTrade> trades = parseFile(file);

        for (CanonicalTrade trade : trades) {
            trade = TradeTransformer.normalize(trade);
            String json = toJson(TradeTransformer.toPlatformJson(trade));
            publisher.send(json);
        }

        return "File processed successfully!";
    }

    private List<CanonicalTrade> parseFile(MultipartFile file) {
        // Implement CSV/JSON parsing logic
        return List.of(); // Placeholder
    }

    private String toJson(Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert to JSON", e);
        }
    }
}
