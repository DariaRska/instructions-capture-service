
package com.example.instructions.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String message) {
        kafkaTemplate.send("instructions.outbound", message);
        System.out.println("Sent to Kafka topic '" + message);
    }
}