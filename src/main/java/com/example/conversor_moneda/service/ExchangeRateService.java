package com.example.conversor_moneda.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.util.Map;

@Service
public class ExchangeRateService {

    @Value("${exchange.rate.api.url}")
    private String apiUrl;

    @Value("${exchange.rate.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public ExchangeRateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Double getExchangeRate(String from, String to) {
        if (from == null || to == null || from.isEmpty() || to.isEmpty()) {
            throw new IllegalArgumentException("Los parámetros 'from' y 'to' no pueden ser nulos o vacíos.");
        }

        String url = apiUrl + apiKey + "/latest/" + from;
        
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response != null && response.containsKey("conversion_rates")) {
                Map<String, Double> rates = (Map<String, Double>) response.get("conversion_rates");
                return rates.getOrDefault(to, null);
            }

        } catch (HttpClientErrorException e) {
            System.err.println("Error en la solicitud HTTP: " + e.getMessage());
        } catch (RestClientException e) {
            System.err.println("Error al conectar con la API: " + e.getMessage());
        }

        return null; // Retorna null si no se encuentra la tasa de cambio
    }
}

