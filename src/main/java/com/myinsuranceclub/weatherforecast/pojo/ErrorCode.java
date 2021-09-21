package com.myinsuranceclub.weatherforecast.pojo;

/**
 * Weather Forecast
 *
 * @author Ranjit Prajapati
 * @version 1.0
 * @since 21-09-2021
 */
public enum ErrorCode {
    //Empty or NULL city name provided
    EMPTY_OR_NULL_CITY(1000),

    //Weather api returned unsuccessful response
    API_NO_SUCCESS(1002),

    //Weather Api No matching city found
    WEATHER_API_400(1003),

    //No Response or location or forecast data not present
    NO_RESPONSE_BODY(1004),

    //Global exception
    UNKNOWN_ERROR(1005);

    public final Integer code;

    ErrorCode(Integer code) {
        this.code = code;
    }

    public Integer getErrorCode() {
        return this.code;
    }
}
