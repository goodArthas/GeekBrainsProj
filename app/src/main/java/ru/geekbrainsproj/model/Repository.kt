package ru.geekbrainsproj.model

import ru.geekbrainsproj.model.pojo.MovieData

interface Repository {
    fun getMoviesFromInternet(): MovieData
    fun getMoviesFromLocalStorage(): MovieData
}