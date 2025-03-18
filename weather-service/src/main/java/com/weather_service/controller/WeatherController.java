package com.weather_service.controller;

import com.weather_service.entity.Weather;
import com.weather_service.repository.WeatherRepository;
import com.weather_service.service.CacheInspectionService;
import com.weather_service.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherRepository weatherRepository;
    @Autowired
    private CacheInspectionService cacheInspectionService;


    @GetMapping
    public String getWeather(@RequestParam String city) {
        String weatherByCity = weatherService.getWeatherByCity(city);
        return weatherByCity;
    }

    @GetMapping("/inspect")
    public void getWeatherByCity() {
        cacheInspectionService.inspectCaches("weather");
    }

    @PostMapping
    public Weather addWeather(@RequestBody Weather weather) {
        return weatherRepository.save(weather);
    }

    @GetMapping("/all")
    public List<Weather> getAllWeather() {
        return weatherRepository.findAll();
    }

    @PutMapping("/{city}")
    public String updateWeather(@PathVariable String city, @RequestParam String weatherUpdate) {
        return weatherService.updateWeather(city, weatherUpdate);
    }
    @DeleteMapping("/{city}")
    public String deleteWeather(@PathVariable String city) {
        weatherService.deleteWeather(city);
        return "Weather data for " + city + " has been deleted and cache evicted.";
    }

}
