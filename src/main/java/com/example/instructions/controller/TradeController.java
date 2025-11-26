
package com.example.instructions.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.instructions.service.TradeService;

@RestController
@RequestMapping("/api/trades")
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @Operation(summary = "Upload trade file", description = "Accepts CSV or JSON file and publishes to Kafka")
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println("Received file");
        return tradeService.processFile(file);
    }
}
