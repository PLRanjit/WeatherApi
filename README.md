# WeatherApi

Sample Application to communicate with microservices

Weather API (3rd party): https://www.weatherapi.com/docs/

Note:
- Change log level to 'DEBUG' to see the details logs
- generate your 'API KEY' from  https://www.weatherapi.com and use it in your application. I will deactivate my key in few days.
- We can change the number of forecast days by updating 'app.forecast.limit' property
  in [application.properties](src/main/resources/application.properties) currently it is set to 3

Requirement / Instructions:

- Make sure 'Lombok' plugin is enabled in the project ![img_2.png](img_2.png)
- Make sure gradle build is supported on your machine
- To build jar from Intellij (gradle  installation not needed) you can follow this ![img_3.png](img_3.png)
- To build jar use command `gradle clean build` (gradle is mandatory)
- If you are unable to build the jar than just download the [weather-forecast.jar](release/weather-forecast-0.0.1-SNAPSHOT.jar)
  and run `java -jar weather-forecast-0.0.1-SNAPSHOT.jar` command in the same folder

Inputs :

- City Name -  city whose weather we want to known

Overview :

- This Application hit's another api internally to get the weather data
- It is a demonstration of how to consume another microservice and handle the exceptions with proper status code

Application URL :

- Forecast Weather Api: http://localhost:8080/micApi/weather/forecast?city={city_name} ![img.png](img.png)
- Current Weather Api: http://localhost:8080/micApi/weather?city={city_name} ![img_1.png](img_1.png)
- {city_name} - can be replaced with city name

CURL:
- Forecast cmd : `curl http://localhost:8080/micApi/weather/forecast?city=Mumbai`
- Current cmd : `curl http://localhost:8080/micApi/weather?city=Mumbai`
