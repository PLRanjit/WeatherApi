package com.myinsuranceclub.weatherforecast.config;

import com.myinsuranceclub.weatherforecast.exception.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Weather Forecast
 *
 * @author Ranjit Prajapati
 * @version 1.0
 * @since 19-09-2021
 */
@Configuration
public class WeatherConfig {

    @Autowired
    private RestTemplateResponseErrorHandler restTemplateResponseErrorHandler;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplateBuilder().errorHandler(restTemplateResponseErrorHandler).build();
    }
}
