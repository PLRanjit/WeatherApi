package com.myinsuranceclub.weatherforecast.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Weather Forecast
 *
 * @author Ranjit Prajapati
 * @version 1.0
 * @since 20-09-2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ForecastResponsePojo {
    List<Map<String, Object>> temperatures;
    private String city;
    private String country;
}
