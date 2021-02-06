package ru.geekbrainsproj

import ru.geekbrainsproj.model.MovieData

sealed class AppState {
    data class Success(val movieArray: ArrayList<MovieData>) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val boolean: Boolean) : AppState()
}
