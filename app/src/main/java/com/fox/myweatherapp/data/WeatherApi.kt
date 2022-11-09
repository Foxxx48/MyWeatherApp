package com.fox.myweatherapp.data

import com.fox.myweatherapp.data.models.WeatherDataModel
import retrofit2.Response
import retrofit2.http.*

interface WeatherApi {

    @GET("weather?q=Moscow" + RetrofitHelper.API_TOKEN + "&units=metric&lang=ru")
    suspend fun getWeatherInMoscow(): Response<WeatherDataModel>

    @POST("")
    suspend fun saveWeatherData(@Body weatherDataModel: WeatherDataModel)

    @DELETE("")
    suspend fun deleteWeatherData(@Path("id") id: Int)


//    yields url=https://api.openweathermap.org/data/2.5/weather&appid=ea45d302d179b4dc2ae37bf6c2f5b5f7?q=Moscow
//    @GET("weather" + RetrofitHelper.API_TOKEN)
//    suspend fun getWeatherData(@Query("q")city: String): Response<WeatherDataModel>


}