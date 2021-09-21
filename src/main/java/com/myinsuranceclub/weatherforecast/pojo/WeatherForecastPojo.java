package com.myinsuranceclub.weatherforecast.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Weather Forecast
 *
 * @author Ranjit Prajapati
 * @version 1.0
 * @since 19-09-2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WeatherForecastPojo {
    private Map<String, Object> location;
    private ForecastPojo forecast;
}
