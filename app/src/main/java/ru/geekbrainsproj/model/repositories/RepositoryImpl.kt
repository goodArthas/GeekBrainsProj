package ru.geekbrainsproj.model.repositories

import retrofit2.Callback
import ru.geekbrainsproj.model.pojo.MovieData

class RepositoryImpl(private val remoteDataSource: RemoteDataSource = RemoteDataSource()) : MainRepository {

    override fun getTopMoviesFromInternet(callback: Callback<MovieData>) {
        remoteDataSource.getTopMovie(callback)
    }

}

