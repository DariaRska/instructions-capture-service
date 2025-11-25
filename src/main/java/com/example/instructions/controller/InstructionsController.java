package com.example.instructions.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructions")
public class InstructionsController {

    @PostMapping
    public String receiveInstructions(@RequestBody String instructions) {
        System.out.println("Received instructions: " + instructions);
        return "Instructions received";
    }
}
