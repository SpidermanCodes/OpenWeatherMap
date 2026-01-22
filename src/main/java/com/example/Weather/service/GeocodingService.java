package com.example.Weather.service;

import com.example.Weather.model.GeocodingResponse;
import com.example.Weather.utils.CityNormalizer;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GeocodingService {

    private final RestTemplate restTemplate;
    private final RateLimiter rateLimiter = RateLimiter.create(1.0 / 1.1); // permits per second

    public GeocodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "geocoding", key = "#city")
    public GeocodingResponse geocodeCity(String city) {
        rateLimiter.acquire(); // blocks until allowed

        String normalizedCityName = CityNormalizer.normalizeCity(city);

        String url = UriComponentsBuilder
                .fromHttpUrl("https://nominatim.openstreetmap.org/search")
                .queryParam("q", normalizedCityName)
                .queryParam("format", "json")
                .queryParam("limit", 1)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "WeatherApp/1.0 (contact: mabe.antonijevic@gmail.com)");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        GeocodingResponse[] response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                GeocodingResponse[].class
        ).getBody();

        if (response == null || response.length == 0) {
            throw new RuntimeException("Location not found: " + city);
        }

        return response[0];
    }
}
