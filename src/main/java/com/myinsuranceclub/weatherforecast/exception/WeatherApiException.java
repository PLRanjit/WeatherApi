package com.myinsuranceclub.weatherforecast.exception;

import com.myinsuranceclub.weatherforecast.pojo.ErrorCode;
import lombok.Getter;

/**
 * Weather Forecast
 *
 * @author Ranjit Prajapati
 * @version 1.0
 * @since 21-09-2021
 */
@Getter
public class WeatherApiException extends RuntimeException {
    private final ErrorCode code;

    public WeatherApiException(String message, ErrorCode errorCode) {
        super(message);
        this.code = errorCode;
    }
}
