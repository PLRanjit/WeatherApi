package com.myinsuranceclub.weatherforecast.exception;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.myinsuranceclub.weatherforecast.pojo.Constants;
import com.myinsuranceclub.weatherforecast.pojo.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Weather Forecast
 *
 * @author Ranjit Prajapati
 * @version 1.0
 * @since 21-09-2021
 */
@Slf4j
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Autowired
    private Gson gson;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR || response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode()
                .series() == HttpStatus.Series.SERVER_ERROR) {
            log.error("RestTemplateResponseErrorHandler ::: handleError :: Internal Server at Weather Api");
        } else if (response.getStatusCode()
                .series() == HttpStatus.Series.CLIENT_ERROR) {
            log.error("RestTemplateResponseErrorHandler ::: handleError :: Client error at Weather Api");
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                String text = new BufferedReader(
                        new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining("\n"));
                Map<String, Map<String, Object>> msgBody = gson.fromJson(text, new TypeReference<Map<String, Map<String, Object>>>() {
                }.getType());
                log.error("RestTemplateResponseErrorHandler ::: handleError :: Not Found :: Status Code : {} :: Message Body {}", response.getStatusCode(), msgBody);
                throw new InvalidCityException(msgBody.get(Constants.WEATHER_API_ERROR).get(Constants.WEATHER_API_MESSAGE).toString(), ErrorCode.NO_RESPONSE_BODY);
            }

            if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                String text = new BufferedReader(
                        new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining("\n"));
                Map<String, Map<String, Object>> msgBody = gson.fromJson(text, new TypeReference<Map<String, Map<String, Object>>>() {
                }.getType());
                log.error("RestTemplateResponseErrorHandler ::: handleError :: Bad Request :: Status Code : {} :: Message Body {}", response.getStatusCode(), msgBody);
                throw new InvalidCityException(msgBody.get(Constants.WEATHER_API_ERROR).get(Constants.WEATHER_API_MESSAGE).toString(), ErrorCode.WEATHER_API_400);
            }
        }
    }
}
