# WeatherApi_Consumer

Sample Application to communicate with microservices

API : https://www.weatherapi.com/docs/

Note: Change log level to 'DEBUG' to see the details logs

Inputs :

- City Name

Overview :

- This hit's another api internally to get the weather data
- It is a demonstration of how to consume another microservice and handle the exceptions with proper status code

Note:

- We can change the number of forecast days by updating 'app.forecast.limit' property
  in [application.properties](src/main/resources/application.properties) currently it is set to 3
-

URL :

- Forecast : http://localhost:8080/micApi/weather/forecast?city=Mumbai ![img.png](img.png)
- Current : http://localhost:8080/micApi/weather?city=Mumbai ![img_1.png](img_1.png)
