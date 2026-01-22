package com.example.Weather.service;

import com.example.Weather.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherService {
    
    private final RestTemplate restTemplate;
    private final GeocodingService geocodingService;
    
    @Value("${api.key}")
    private String apiKey;
    
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5";
    private static final String AIR_POLLUTION_URL = "http://api.openweathermap.org/data/2.5";
    private static final String UV_INDEX_URL = "http://api.openweathermap.org/data/2.5";
    
    public WeatherService(RestTemplate restTemplate, GeocodingService geocodingService) {
        this.restTemplate = restTemplate;
        this.geocodingService = geocodingService;
    }
    
    @Cacheable(value = "currentWeather", key = "#city")
    public CurrentWeatherResponse getCurrentWeather(String city) {

        GeocodingResponse geocodingResponse = geocodingService.geocodeCity(city);

        System.out.println("CALLING WEATHER API for: " + city);
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/weather")
                .queryParam("lat", geocodingResponse.getLat())
                .queryParam("lon", geocodingResponse.getLon())
//                .queryParam("q", city)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .toUriString();
        
        try {
            return restTemplate.getForObject(url, CurrentWeatherResponse.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new RuntimeException("City '" + city + "' not found. Please check the spelling and try again.");
            }
            throw new RuntimeException("Error fetching weather data: " + e.getStatusText());
        } catch (RestClientException e) {
            throw new RuntimeException("Unable to connect to weather service. Please try again later.");
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred: " + e.getMessage());
        }
    }
    
    public CurrentWeatherResponse getCurrentWeatherByCoordinates(double lat, double lon) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/weather")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .toUriString();
        
        try {
            return restTemplate.getForObject(url, CurrentWeatherResponse.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new RuntimeException("Weather data not available for this location.");
            }
            throw new RuntimeException("Error fetching weather data: " + e.getStatusText());
        } catch (RestClientException e) {
            throw new RuntimeException("Unable to connect to weather service. Please try again later.");
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred: " + e.getMessage());
        }
    }
    
    @Cacheable(value = "forecast", key = "#city")
    public ForecastResponse getForecast(String city) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/forecast")
                .queryParam("q", city)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .toUriString();
        
        try {
            return restTemplate.getForObject(url, ForecastResponse.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new RuntimeException("City '" + city + "' not found. Please check the spelling and try again.");
            }
            throw new RuntimeException("Error fetching forecast: " + e.getStatusText());
        } catch (RestClientException e) {
            throw new RuntimeException("Unable to connect to weather service. Please try again later.");
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred: " + e.getMessage());
        }
    }
    
    public AirPollutionResponse getAirPollution(double lat, double lon) {
        String url = UriComponentsBuilder.fromHttpUrl(AIR_POLLUTION_URL + "/air_pollution")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("appid", apiKey)
                .toUriString();
        
        try {
            return restTemplate.getForObject(url, AirPollutionResponse.class);
        } catch (Exception e) {
            // Air pollution is optional, return null instead of throwing
            return null;
        }
    }
    
    public UVIndexResponse getUVIndex(double lat, double lon) {
        String url = UriComponentsBuilder.fromHttpUrl(UV_INDEX_URL + "/uvi")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("appid", apiKey)
                .toUriString();
        
        try {
            return restTemplate.getForObject(url, UVIndexResponse.class);
        } catch (Exception e) {
            // UV index is optional, return null instead of throwing
            return null;
        }
    }
    
    public Map<String, CurrentWeatherResponse> compareCities(String... cities) {
        Map<String, CurrentWeatherResponse> comparison = new HashMap<>();
        for (String city : cities) {
            try {
                comparison.put(city, getCurrentWeather(city));
            } catch (Exception e) {
                // Skip cities that fail
            }
        }
        return comparison;
    }
    
    public String getWeatherIconUrl(String iconCode) {
        return "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";
    }
    
    public String getAQIDescription(int aqi) {
        switch (aqi) {
            case 1: return "Good";
            case 2: return "Fair";
            case 3: return "Moderate";
            case 4: return "Poor";
            case 5: return "Very Poor";
            default: return "Unknown";
        }
    }
    
    public String getUVIndexDescription(double uvIndex) {
        if (uvIndex < 3) return "Low";
        if (uvIndex < 6) return "Moderate";
        if (uvIndex < 8) return "High";
        if (uvIndex < 11) return "Very High";
        return "Extreme";
    }
}

