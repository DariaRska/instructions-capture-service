package com.example.instructions.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InstructionProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public InstructionProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, String message) {
        kafkaTemplate.send(topic, message);
        System.out.println("Sent to Kafka topic '" + topic + "': " + message);
    }
}