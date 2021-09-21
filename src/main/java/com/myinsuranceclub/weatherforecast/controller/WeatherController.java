package com.myinsuranceclub.weatherforecast.controller;

import com.myinsuranceclub.weatherforecast.exception.InvalidCityException;
import com.myinsuranceclub.weatherforecast.pojo.ErrorCode;
import com.myinsuranceclub.weatherforecast.pojo.ForecastResponsePojo;
import com.myinsuranceclub.weatherforecast.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Weather Forecast
 *
 * @author Ranjit Prajapati
 * @version 1.0
 * @since 19-09-2021
 */
@RestController
@Slf4j
@RequestMapping("micApi")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("weather/forecast")
    public ForecastResponsePojo getForecast(@RequestParam String city) {
        log.info("WeatherController ::: getForecast :: City : {}", city);
        if (city == null || city.trim().isEmpty()) {
            throw new InvalidCityException("City Name is null or empty", ErrorCode.EMPTY_OR_NULL_CITY);
        }
        return weatherService.getForecast(city);
    }

    @GetMapping("weather")
    public Map<String, Object> getTodaysWeather(@RequestParam String city) {
        log.info("WeatherController ::: getTodaysWeather :: City : {}", city);
        if (city == null || city.trim().isEmpty()) {
            throw new InvalidCityException("City Name is null or empty", ErrorCode.EMPTY_OR_NULL_CITY);
        }
        return weatherService.getTodaysWeather(city);
    }
}
