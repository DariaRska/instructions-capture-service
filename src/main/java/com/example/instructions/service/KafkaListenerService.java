
package com.example.instructions.service;

import com.example.instructions.model.CanonicalTrade;
import com.example.instructions.util.TradeTransformer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaListenerService {

    private final KafkaPublisher publisher;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaListenerService(KafkaPublisher publisher) {
        this.publisher = publisher;
    }

    @KafkaListener(topics = "instructions.inbound", groupId = "trade-group")
    public void consume(String message) {
        /* Parse JSON to CanonicalTrade */
        CanonicalTrade trade = parseJson(message);

        /* Normalize */
        CanonicalTrade normalized = TradeTransformer.normalize(trade);

        /* Convert to PlatformTrade JSON */
        String platformJson = toJson(TradeTransformer.toPlatformJson(normalized));

        /* Publish to outbound topic */
        publisher.send(platformJson);
    }

    private CanonicalTrade parseJson(String json) {
        try {
            return objectMapper.readValue(json, CanonicalTrade.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

    private String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert to JSON", e);
        }
    }
}
