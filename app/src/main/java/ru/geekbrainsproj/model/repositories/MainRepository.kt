package ru.geekbrainsproj.model.repositories

import retrofit2.Callback
import ru.geekbrainsproj.model.pojo.MovieData

interface MainRepository {
    fun getTopMoviesFromInternet(callback: Callback<MovieData>)
}