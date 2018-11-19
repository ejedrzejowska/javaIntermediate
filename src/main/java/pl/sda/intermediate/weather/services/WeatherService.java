package pl.sda.intermediate.weather.services;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.intermediate.users.UserContextHolder;
import pl.sda.intermediate.users.UserDAO;
import pl.sda.intermediate.users.UserLoginDTO;
import pl.sda.intermediate.weather.model.WeatherResult;
import retrofit2.Retrofit;
import retrofit2.adapter.java8.Java8CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.CompletableFuture;

@Service
public class WeatherService {

    private String key = "ea900b66f547fd7b23625544873a4200";
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserContextHolder userContextHolder;

    public String getWeather(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(Java8CallAdapterFactory.create())
                .build();

        OpenWeatherMapJ8 weatherService = retrofit.create(OpenWeatherMapJ8.class);
        CompletableFuture<WeatherResult> completableFuture = weatherService.currentByCity("Berlin", key, "metric", "en");
//        CompletableFuture<WeatherResult> completableFuture = weatherService.currentByCity(userDAO.getCity(), key, userDAO.getUnits(), userDAO.getLang());
        WeatherResult weatherResult = completableFuture.join();
        return new Gson().toJson(weatherResult);
    }
}
