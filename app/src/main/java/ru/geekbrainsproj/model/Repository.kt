package ru.geekbrainsproj.model

interface Repository {
    fun getMoviesFromInternet(): ArrayList<MovieData>
    fun getMoviesFromLocalStorage(): ArrayList<MovieData>
}