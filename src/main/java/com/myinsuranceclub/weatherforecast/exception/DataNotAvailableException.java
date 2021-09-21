package com.myinsuranceclub.weatherforecast.exception;

import com.myinsuranceclub.weatherforecast.pojo.ErrorCode;
import lombok.Getter;

/**
 * Weather Forecast
 *
 * <p>If no response body is found or location or forecast or current data is not present handling
 *
 * @author Ranjit Prajapati
 * @version 1.0
 * @since 21-09-2021
 */
@Getter
public class DataNotAvailableException extends RuntimeException {
    private final ErrorCode code;

    public DataNotAvailableException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }
}
