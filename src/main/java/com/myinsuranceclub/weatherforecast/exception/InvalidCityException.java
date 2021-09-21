package com.myinsuranceclub.weatherforecast.exception;

import com.myinsuranceclub.weatherforecast.pojo.ErrorCode;
import lombok.Getter;

/**
 * Weather Forecast
 *
 * <p>Empty Or Null or unknown City name handling</p>
 *
 * @author Ranjit Prajapati
 * @version 1.0
 * @since 20-09-2021
 */

@Getter
public class InvalidCityException extends RuntimeException {
    private final ErrorCode code;

    public InvalidCityException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }
}
