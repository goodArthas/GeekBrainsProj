package ru.geekbrainsproj.model

import android.util.Log

class RepositoryImpl : Repository {

    private fun getArrays(): ArrayList<MovieData> = ArrayList<MovieData>().apply { for (i in 1..50) add(MovieData.getDefaultMovieData()) }

    override fun getMoviesFromInternet(): ArrayList<MovieData> {
        return ArrayList<MovieData>()
    }

    override fun getMoviesFromLocalStorage(): ArrayList<MovieData> {
        Log.d("QWE", "getMoviesFromLocalStorage: ")
        return getArrays()
    }

}

