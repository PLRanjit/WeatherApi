package com.myinsuranceclub.weatherforecast.service;

import com.myinsuranceclub.weatherforecast.exception.DataNotAvailableException;
import com.myinsuranceclub.weatherforecast.exception.WeatherApiException;
import com.myinsuranceclub.weatherforecast.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Weather Forecast
 *
 * @author Ranjit Prajapati
 * @version 1.0
 * @since 19-09-2021
 */
@Service
@Slf4j
public class WeatherService {

    @Value("${app.forecast.limit}")
    private Integer forecastLimit;

    @Value("${app.weather.api}")
    private String baseUrl;

    @Value("${app.weather.api.current}")
    private String currentApi;

    @Value("${app.weather.api.key}")
    private String apiKey;

    @Value("${app.weather.api.forecast}")
    private String forecastApi;

    @Value("${app.weather.api.request.param.city}")
    private String requestParamDays;

    @Value("${app.weather.api.request.param.days}")
    private String requestParamCity;


    @Autowired
    private RestTemplate restTemplate;

    /**
     * Getting forecast data from Weather API
     *
     * @param city whose weather report is needed
     * @return City, country, min max & avg temperature prediction
     */
    public ForecastResponsePojo getForecast(String city) {
        log.info("WeatherService ::: getForecast :: City : {}", city);
        Map<String, Object> params = new HashMap<>();
        params.put(Constants.WEATHER_API_QUERY_KEY, apiKey);
        params.put(Constants.CITY, city);
        params.put(Constants.WEATHER_API_QUERY_DAY, forecastLimit);
        String url = String.format("%s%s?key={key}&q={city}&days={day}", baseUrl, forecastApi);
        ResponseEntity<WeatherForecastPojo> weatherDataResp = restTemplate.getForEntity(url, WeatherForecastPojo.class, params);
        log.debug("WeatherService ::: city : {} :: response : {}", city, weatherDataResp.getStatusCode());

        if (weatherDataResp.getStatusCode() != HttpStatus.OK) {
            log.error("WeatherService ::: getForecast :: status code : {}, message : {}", weatherDataResp.getStatusCode(), weatherDataResp.getBody());
            throw new WeatherApiException("Unable to get weather details", ErrorCode.API_NO_SUCCESS);
        }

        WeatherForecastPojo weatherData = weatherDataResp.getBody();
        if (weatherData == null || weatherData.getForecast() == null || weatherData.getLocation() == null) {
            log.error("WeatherService ::: getForecast :: status code : {}, message : {}", weatherDataResp.getStatusCode(), weatherDataResp.getBody());
            throw new DataNotAvailableException("No Data Found", ErrorCode.NO_RESPONSE_BODY);
        }

        Map<String, Object> location = weatherData.getLocation();
        String cityName = location.get(Constants.WEATHER_API_RESPONSE_NAME).toString();
        String country = location.get(Constants.WEATHER_API_RESPONSE_COUNTRY).toString();
        ForecastPojo forecastPojo = weatherData.getForecast();
        List<ForecastDayPojo> forecastDays = forecastPojo.getForecastday();
        List<Map<String, Object>> dayData = forecastDays.stream().map(forecast -> {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("date", forecast.getDate());
            Map<String, Object> day = forecast.getDay();
            row.put(Constants.MAX_TEMP_CELSIUS, day.get(Constants.WEATHER_API_RESPONSE_MAXTEMP_C));
            row.put(Constants.MIN_TEMP_CELSIUS, day.get(Constants.WEATHER_API_RESPONSE_MINTEMP_C));
            row.put(Constants.MAX_TEMP_FAHRENHEIT, day.get(Constants.WEATHER_API_RESPONSE_MAXTEMP_F));
            row.put(Constants.MIN_TEMP_FAHRENHEIT, day.get(Constants.WEATHER_API_RESPONSE_MINTEMP_F));
            row.put(Constants.AVG_TEMP_CELSIUS, day.get(Constants.WEATHER_API_RESPONSE_AVGTEMP_C));
            row.put(Constants.AVG_TEMP_FAHRENHEIT, day.get(Constants.WEATHER_API_RESPONSE_AVGTEMP_F));
            return row;
        }).collect(Collectors.toList());

        ForecastResponsePojo response = new ForecastResponsePojo();
        response.setCity(cityName);
        response.setCountry(country);
        response.setTemperatures(dayData);
        return response;
    }

    /**
     * Get weather forecast data for current day
     *
     * @param city whose weather report is needed
     * @return City, country, temperature
     */
    public Map<String, Object> getTodaysWeather(String city) {
        log.info("WeatherService ::: getTodaysWeather :: City : {}", city);
        Map<String, Object> params = new HashMap<>();
        params.put(Constants.WEATHER_API_QUERY_KEY, apiKey);
        params.put(Constants.CITY, city);
        String url = String.format("%s%s?key={key}&q={city}", baseUrl, currentApi);
        ResponseEntity<WeatherTodayPojo> todayResponseResp = restTemplate.getForEntity(url, WeatherTodayPojo.class, params);
        if (todayResponseResp.getStatusCode() != HttpStatus.OK) {
            log.error("WeatherService ::: getTodaysWeather :: status code : {}, message : {}", todayResponseResp.getStatusCode(), todayResponseResp.getBody());
            throw new WeatherApiException("Unable to get weather details", ErrorCode.API_NO_SUCCESS);
        }
        WeatherTodayPojo todayResponse = todayResponseResp.getBody();
        if (todayResponse == null || todayResponse.getLocation() == null || todayResponse.getCurrent() == null) {
            log.error("WeatherService ::: getTodaysWeather :: status code : {}, message : {}", todayResponseResp.getStatusCode(), todayResponseResp.getBody());
            throw new DataNotAvailableException("No Data Found", ErrorCode.NO_RESPONSE_BODY);
        }

        Map<String, Object> response = new LinkedHashMap<>();
        Map<String, Object> location = todayResponse.getLocation();
        String cityName = location.get(Constants.WEATHER_API_RESPONSE_NAME).toString();
        String country = location.get(Constants.WEATHER_API_RESPONSE_COUNTRY).toString();

        response.put(Constants.CITY, cityName);
        response.put(Constants.COUNTRY, country);
        Map<String, Object> currentDay = todayResponse.getCurrent();
        response.put(Constants.TEMP_CELSIUS, currentDay.get(Constants.WEATHER_API_RESPONSE_TEMP_C));
        response.put(Constants.TEMP_FAHRENHEIT, currentDay.get(Constants.WEATHER_API_RESPONSE_TEMP_F));
        return response;
    }
}
