package com.example.Weather.controller;

import com.example.Weather.model.*;
import com.example.Weather.repository.FavoriteCityRepository;
import com.example.Weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class WeatherController {
    
    @Autowired
    private WeatherService weatherService;
    
    @Autowired
    private FavoriteCityRepository favoriteCityRepository;
    
    @GetMapping("/")
    public String getIndex(Model model) {
        List<FavoriteCity> favorites = favoriteCityRepository.findAll();
        model.addAttribute("favorites", favorites);
        return "index";
    }
    
    @GetMapping("/weather")
    public String getWeather(@RequestParam(value = "city", required = false) String city,
                            @RequestParam(value = "lat", required = false) Double lat,
                            @RequestParam(value = "lon", required = false) Double lon,
                            @RequestParam(value = "returnTo", required = false) String returnTo,
                            @RequestParam(value = "citiesString", required = false) String citiesString,
                            @RequestParam(value = "mapLat", required = false) Double mapLat,
                            @RequestParam(value = "mapLon", required = false) Double mapLon,
                            @RequestParam(value = "mapZoom", required = false) Integer mapZoom,
                            @RequestParam(value = "mapScroll", required = false) Double mapScroll,
                            Model model) {
        try {
            CurrentWeatherResponse weather;
            
            // Get weather by coordinates if provided, otherwise by city name
            if (lat != null && lon != null) {
                weather = weatherService.getCurrentWeatherByCoordinates(lat, lon);
            } else if (city != null && !city.isEmpty()) {
                weather = weatherService.getCurrentWeather(city);
            } else {
                model.addAttribute("error", "Please provide either a city name or coordinates.");
                return "weather";
            }
            
            if (weather != null && weather.getWeather() != null && !weather.getWeather().isEmpty()) {
                model.addAttribute("city", weather.getName());
                model.addAttribute("country", weather.getSys().getCountry());
                model.addAttribute("weatherDescription", weather.getWeather().get(0).getDescription());
                model.addAttribute("weatherMain", weather.getWeather().get(0).getMain());
                model.addAttribute("temperature", Math.round(weather.getMain().getTemp()));
                model.addAttribute("feelsLike", Math.round(weather.getMain().getFeelsLike()));
                model.addAttribute("tempMin", Math.round(weather.getMain().getTempMin()));
                model.addAttribute("tempMax", Math.round(weather.getMain().getTempMax()));
                model.addAttribute("humidity", weather.getMain().getHumidity());
                model.addAttribute("pressure", weather.getMain().getPressure());
                model.addAttribute("windSpeed", weather.getWind().getSpeed());
                model.addAttribute("windDeg", weather.getWind().getDeg());
                model.addAttribute("clouds", weather.getClouds().getAll());
                model.addAttribute("visibility", weather.getVisibility() / 1000.0); // Convert to km
                model.addAttribute("sunrise", new Date(weather.getSys().getSunrise() * 1000));
                model.addAttribute("sunset", new Date(weather.getSys().getSunset() * 1000));
                model.addAttribute("weatherIcon", weather.getWeather().get(0).getIcon());
                model.addAttribute("weatherIconUrl", weatherService.getWeatherIconUrl(weather.getWeather().get(0).getIcon()));
                model.addAttribute("coordinates", weather.getCoord());
                
                // Get additional data
                try {
                    AirPollutionResponse airPollution = weatherService.getAirPollution(
                        weather.getCoord().getLat(), 
                        weather.getCoord().getLon()
                    );
                    if (airPollution != null && !airPollution.getList().isEmpty()) {
                        AirPollutionResponse.AirPollutionItem current = airPollution.getList().get(0);
                        model.addAttribute("aqi", current.getMain().getAqi());
                        model.addAttribute("aqiDescription", weatherService.getAQIDescription(current.getMain().getAqi()));
                        model.addAttribute("airComponents", current.getComponents());
                    }
                } catch (Exception e) {
                    // Air pollution data not available
                }
                
                try {
                    UVIndexResponse uvIndex = weatherService.getUVIndex(
                        weather.getCoord().getLat(), 
                        weather.getCoord().getLon()
                    );
                    if (uvIndex != null) {
                        model.addAttribute("uvIndex", Math.round(uvIndex.getValue() * 10) / 10.0);
                        model.addAttribute("uvIndexDescription", weatherService.getUVIndexDescription(uvIndex.getValue()));
                    }
                } catch (Exception e) {
                    // UV index not available
                }
                
                model.addAttribute("isFavorite", favoriteCityRepository.existsByCityNameIgnoreCase(city));
                
                // Navigation support for comparison view
                if ("compare".equals(returnTo) && citiesString != null && !citiesString.isEmpty()) {
                    model.addAttribute("returnTo", "compare");
                    String returnUrl = UriComponentsBuilder.fromPath("/compare")
                            .queryParam("cities", citiesString)
                            .toUriString();
                    model.addAttribute("returnUrl", returnUrl);
                }
                
                // Navigation support for map view
                if ("map".equals(returnTo) && mapLat != null && mapLon != null) {
                    model.addAttribute("returnTo", "map");
                    UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromPath("/map")
                            .queryParam("mapLat", mapLat)
                            .queryParam("mapLon", mapLon)
                            .queryParam("mapZoom", mapZoom != null ? mapZoom : 10);
                    if (mapScroll != null) {
                        urlBuilder.queryParam("mapScroll", mapScroll);
                    }
                    model.addAttribute("returnUrl", urlBuilder.toUriString());
                }
            } else {
                model.addAttribute("error", "City not found or weather data unavailable.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error retrieving weather data: " + e.getMessage());
        }
        
        return "weather";
    }
    
    @GetMapping("/forecast")
    public String getForecast(@RequestParam("city") String city, Model model) {
        try {
            ForecastResponse forecast = weatherService.getForecast(city);
            
            if (forecast != null && forecast.getList() != null) {
                model.addAttribute("city", forecast.getCity().getName());
                model.addAttribute("country", forecast.getCity().getCountry());
                
                // Group forecast by date
                Map<String, List<ForecastResponse.ForecastItem>> forecastByDate = forecast.getList().stream()
                    .collect(Collectors.groupingBy(item -> {
                        Date date = new Date(item.getDt() * 1000);
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        return cal.get(Calendar.YEAR) + "-" + 
                               String.format("%02d", cal.get(Calendar.MONTH) + 1) + "-" + 
                               String.format("%02d", cal.get(Calendar.DAY_OF_MONTH));
                    }));
                
                model.addAttribute("forecastByDate", forecastByDate);
                model.addAttribute("forecastList", forecast.getList());
            } else {
                model.addAttribute("error", "Forecast data not available.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error retrieving forecast: " + e.getMessage());
        }
        
        return "forecast";
    }
    
    @GetMapping("/compare")
    public String getCompare(@RequestParam(value = "cities", required = false) String cities, Model model) {
        if (cities != null && !cities.isEmpty()) {
            String[] cityArray = cities.split(",");
            // Trim whitespace from each city name
            for (int i = 0; i < cityArray.length; i++) {
                cityArray[i] = cityArray[i].trim();
            }
            Map<String, CurrentWeatherResponse> comparison = weatherService.compareCities(cityArray);
            model.addAttribute("comparison", comparison);
            model.addAttribute("cities", Arrays.asList(cityArray));
            model.addAttribute("citiesString", cities); // Keep original string for input field
        } else {
            model.addAttribute("citiesString", "");
        }
        return "compare";
    }
    
    @PostMapping("/favorites/add")
    public String addFavorite(@RequestParam("city") String city, 
                             @RequestParam("country") String country,
                             @RequestParam(value = "lat", required = false) Double lat,
                             @RequestParam(value = "lon", required = false) Double lon,
                             RedirectAttributes redirectAttributes) {
        try {
            if (!favoriteCityRepository.existsByCityNameIgnoreCase(city)) {
                FavoriteCity favorite = new FavoriteCity();
                favorite.setCityName(city);
                favorite.setCountry(country);
                favorite.setLatitude(lat);
                favorite.setLongitude(lon);
                favoriteCityRepository.save(favorite);
                redirectAttributes.addFlashAttribute("success", city + " added to favorites!");
            } else {
                redirectAttributes.addFlashAttribute("error", city + " is already in favorites!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding favorite: " + e.getMessage());
        }
        return "redirect:/";
    }
    
    @PostMapping("/favorites/remove")
    public String removeFavorite(@RequestParam("city") String city, RedirectAttributes redirectAttributes) {
        try {
            favoriteCityRepository.findByCityNameIgnoreCase(city)
                .ifPresent(favoriteCityRepository::delete);
            redirectAttributes.addFlashAttribute("success", city + " removed from favorites!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error removing favorite: " + e.getMessage());
        }
        return "redirect:/";
    }
    
    @GetMapping("/map")
    public String getMap(@RequestParam(value = "lat", required = false) Double lat,
                        @RequestParam(value = "lon", required = false) Double lon,
                        @RequestParam(value = "mapLat", required = false) Double mapLat,
                        @RequestParam(value = "mapLon", required = false) Double mapLon,
                        @RequestParam(value = "mapZoom", required = false) Integer mapZoom,
                        Model model) {
        // If map state parameters are provided (returning from weather page), use those
        // Otherwise, use provided lat/lon or default to London
        if (mapLat != null && mapLon != null) {
            model.addAttribute("lat", mapLat);
            model.addAttribute("lon", mapLon);
        } else {
            model.addAttribute("lat", lat != null ? lat : 51.5074);
            model.addAttribute("lon", lon != null ? lon : -0.1278);
        }
        return "map";
    }
}
