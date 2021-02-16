package ru.geekbrainsproj.model

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.JobIntentService
import com.google.gson.Gson
import ru.geekbrainsproj.model.pojo.MovieData
import ru.geekbrainsproj.view.ACTION_FILTER_MOVIE_BR
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val MOVIE_DATA = "dataMovie"

class MyService() : JobIntentService() {

    private val brIntent = Intent(ACTION_FILTER_MOVIE_BR)

    private val REQUEST_GET = "GET"
    private val REQUEST_TIMEOUT = 10000
    private val API_KEY = "c60894a17682b2d9baf9b6f814435cc8"
    private val language = "en-US"
    private val page = 1


    companion object {
        fun run(context: Context, intent: Intent) {
            enqueueWork(context, MyService::class.java, 5622, intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleWork(intent: Intent) {
        loadMovieList()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadMovieList() {
        try {
            log("loadMovieList")
            val uri =
                    URL("https://api.themoviedb.org/3/movie/top_rated?api_key=${API_KEY}&language=${language}&page=${page}")
            lateinit var urlConnection: HttpsURLConnection
            try {
                log("try to urlConnection")
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.apply {
                    requestMethod = REQUEST_GET
                    readTimeout = REQUEST_TIMEOUT
                    addRequestProperty(API_KEY,
                            API_KEY
                    )
                }

                val movieData: MovieData =
                        Gson().fromJson(
                                getLines(BufferedReader(InputStreamReader(urlConnection.inputStream))),
                                MovieData::class.java
                        )
                onResponse(movieData)
            } catch (e: Exception) {
                //onErrorRequest(e.message ?: "Empty error")
                log("catch (e: Exception) ${e.printStackTrace()}")
            } finally {
                urlConnection.disconnect()
                log("finally disconnect")

            }
        } catch (e: MalformedURLException) {
            //onMalformedURL()
            log("catch (e: MalformedURLException) ${e.printStackTrace()}")
        }

    }

    private fun onResponse(movieData: MovieData) {
        log("onResponse")
        val listInfo = movieData.movieInfo
        log("listInfo?.let BEFORE")
        listInfo?.let {
            log("listInfo?.let IN")
            onSuccessResponse(movieData)
        }

    }


    private fun onSuccessResponse(movieData: MovieData) {
        sendBroadcast(brIntent.apply {
            putExtra(MOVIE_DATA, movieData)
            log("Take to ${movieData.movieInfo?.size}")
        })
    }

    private fun log(text: String) = Log.e("Serv", text)

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }


}