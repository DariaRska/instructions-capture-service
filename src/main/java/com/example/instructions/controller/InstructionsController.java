package com.example.instructions.controller;

import com.example.instructions.service.InstructionProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructions")
public class InstructionsController {

    private final InstructionProducer producer;

    public InstructionsController(InstructionProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public String receiveInstructions(@RequestBody String instructions) {

        // Print to console
        System.out.println("Received instructions: " + instructions);

        // Send to Kafka
        producer.send("instructions-topic", instructions);

        return "Instructions sent to Kafka";
    }
}
