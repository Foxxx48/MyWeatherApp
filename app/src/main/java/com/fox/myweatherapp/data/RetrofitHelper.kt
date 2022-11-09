package com.fox.myweatherapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    fun getWeatherApi() : WeatherApi {
        var retrofit = Retrofit.Builder()
            .baseUrl(LINK)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var weatherApi : WeatherApi = retrofit.create(WeatherApi::class.java)
        return weatherApi
    }

    private const val LINK = "https://api.openweathermap.org/data/2.5/"
     const val API_TOKEN = "&appid=ea45d302d179b4dc2ae37bf6c2f5b5f7"

//    https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}

//    "https://openweathermap.org/find?q=Moscow"

}