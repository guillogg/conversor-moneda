package com.example.conversor_moneda.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.conversor_moneda.service.ExchangeRateService;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/rate")
    public ResponseEntity<?> getExchangeRate(@RequestParam String from, @RequestParam String to) {
        try {
            Double rate = exchangeRateService.getExchangeRate(from, to);

            if (rate == null) {
                return ResponseEntity.badRequest().body("No se encontró la tasa de cambio para " + from + " a " + to);
            }

            return ResponseEntity.ok(rate);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
