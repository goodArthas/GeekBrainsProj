package ru.geekbrainsproj.model.repositories.remote

import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.geekbrainsproj.model.pojo.MovieData

class RemoteDataSource {

    private val baseUrl = "https://api.themoviedb.org/3/movie/"
    private val apiKey = "c60894a17682b2d9baf9b6f814435cc8"

    // private val language = "en-US"
    private val language = "ru-RU"
    private val page = 1

    private val movieApi = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build().create(MovieAPI::class.java)

    fun getTopMovie(callback: Callback<MovieData>) {
        movieApi.getTopMovie(apiKey, language, page).enqueue(callback)
    }

}