package ru.geekbrainsproj

import ru.geekbrainsproj.model.pojo.MovieData

sealed class AppState {
    data class Success(val movieData: MovieData) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val boolean: Boolean) : AppState()
}
