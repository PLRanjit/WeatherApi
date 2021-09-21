package com.myinsuranceclub.weatherforecast.exception;

import com.myinsuranceclub.weatherforecast.pojo.ErrorCode;
import com.myinsuranceclub.weatherforecast.pojo.ErrorPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Weather Forecast
 *
 * @author Ranjit Prajapati
 * @version 1.0
 * @since 19-09-2021
 */
@RestControllerAdvice
@Slf4j
public class WeatherExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InvalidCityException.class})
    public ResponseEntity<Object> handleInvalidInputException(InvalidCityException ex) {
        log.error("Invalid City Exception: {}", ex.getMessage());
        ErrorPojo errorPojo = new ErrorPojo(ex.getCode().getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(errorPojo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {WeatherApiException.class})
    public ResponseEntity<Object> handleWeatherApiException(WeatherApiException ex) {
        log.error("Weather Api no response Exception: {}", ex.getMessage());
        ErrorPojo errorPojo = new ErrorPojo(ex.getCode().getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(errorPojo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {DataNotAvailableException.class})
    public ResponseEntity<Object> handleWeatherApiException(DataNotAvailableException ex) {
        log.error("Weather Api Response body empty or location or forecast or current data not present Exception: {}", ex.getMessage());
        ErrorPojo errorPojo = new ErrorPojo(ex.getCode().getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(errorPojo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleGlobalException(Exception ex) {
        log.error("Weather Api Response body empty or location or forecast or current data not present Exception: {}", ex.getMessage());
        ErrorPojo errorPojo = new ErrorPojo(ErrorCode.UNKNOWN_ERROR.getErrorCode(), "Something Went wrong Contact DEV Team");
        return new ResponseEntity<>(errorPojo, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
