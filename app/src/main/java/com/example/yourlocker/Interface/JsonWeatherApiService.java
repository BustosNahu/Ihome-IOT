package com.example.yourlocker.Interface;

import com.example.yourlocker.Model.ApiWeather.WeatherTemperature;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonWeatherApiService {

    @GET("data/2.5/weather?")
    Call<WeatherTemperature> getTemperature(@Query("q") String cityName,
                                            @Query("appid") String apikey, @Query("units") String units);
}
