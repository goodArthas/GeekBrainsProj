package ru.geekbrainsproj.model.repositories

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.geekbrainsproj.model.pojo.MovieData

interface MovieAPI {

    @GET("top_rated")
    fun getTopMovie(@Query("api_key") apiKey: String,
                    @Query("language") language: String,
                    @Query("page") page: Int
    ): Call<MovieData>

}