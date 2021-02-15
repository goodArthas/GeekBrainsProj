package ru.geekbrainsproj.model

import android.util.Log
import ru.geekbrainsproj.model.pojo.MovieData

class RepositoryImpl : Repository {


    override fun getMoviesFromInternet(): MovieData {
        return null!!
    }

    override fun getMoviesFromLocalStorage(): MovieData {
        Log.d("QWE", "getMoviesFromLocalStorage: ")
        return null!!
    }

}

