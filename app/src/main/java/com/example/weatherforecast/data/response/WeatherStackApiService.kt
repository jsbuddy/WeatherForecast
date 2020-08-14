package com.example.weatherforecast.data.response

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "45301140f50a799c1eaf1572675ae7e2";

interface WeatherStackApiService {
    @GET("current")
    suspend fun getCurrentWeather(@Query("query") location: String): CurrentWeatherResponse

    companion object {
        operator fun invoke(): WeatherStackApiService {
            val interceptor = Interceptor { chain ->
                val url = chain.request().url().newBuilder()
                    .addQueryParameter("access_key", API_KEY).build()

                val request = chain.request().newBuilder().url(url).build()

                return@Interceptor chain.proceed(request)
            }

            val okHttp = OkHttpClient.Builder().addInterceptor(interceptor).build()

            return Retrofit.Builder().client(okHttp)
                .baseUrl("http://api.weatherstack.com/")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(WeatherStackApiService::class.java)
        }
    }
}