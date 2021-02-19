package ru.geekbrainsproj.model.pojo

import okhttp3.Request
import retrofit2.Callback

class RemoteDataSource {


    fun getMovieList(requestLink: String, callback: Callback<MovieData>) {

        val builder: Request.Builder = Request.Builder().apply {
            url(requestLink)
        }


    }

}