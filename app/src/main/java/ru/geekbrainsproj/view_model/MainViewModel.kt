package ru.geekbrainsproj.view_model


import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrainsproj.App
import ru.geekbrainsproj.AppState
import ru.geekbrainsproj.model.pojo.MovieData
import ru.geekbrainsproj.model.pojo.MovieInfo
import ru.geekbrainsproj.model.repositories.MainRepository
import ru.geekbrainsproj.model.repositories.RepositoryImpl
import ru.geekbrainsproj.model.repositories.locale.LocalRepository
import ru.geekbrainsproj.model.repositories.locale.LocalRepositoryImpl
import ru.geekbrainsproj.model.room.WatchLaterFilmEntity
import java.text.SimpleDateFormat
import java.util.*

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class MainViewModel(private val mainRepository: MainRepository = RepositoryImpl(),
                    private val localRepository: LocalRepository = LocalRepositoryImpl(App.getWatchLaterDao())
) : ViewModel(), LifecycleObserver {

    val liveData: MutableLiveData<AppState> = MutableLiveData()
    val liveDataLoading: MutableLiveData<AppState.Loading> = MutableLiveData()

    private val callback =
            object : Callback<MovieData> {
                override fun onResponse(call: Call<MovieData>, response: Response<MovieData>) {
                    val serverResponse: MovieData? = response.body()

                    liveData.postValue(
                            if (response.isSuccessful && serverResponse != null) {
                                checkResponse(serverResponse)
                            } else {
                                AppState.Error(Throwable(SERVER_ERROR))
                            }

                    )

                    liveDataLoading.postValue(AppState.Loading(false))

                }

                override fun onFailure(call: Call<MovieData>, t: Throwable) {
                    liveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
                    liveDataLoading.postValue(AppState.Loading(false))
                }
            }


    companion object {
        const val NAME_FILM = "film_name"
        const val DESCRIBE_FILM = "film_describe"
        const val POSTER_FILM = "film_poster"
    }

    init {
        onCreate()
    }

    private fun onCreate() {
        getDataFromInternet()
    }

    private fun getDataFromInternet() {
        liveDataLoading.value = AppState.Loading(true)
        mainRepository.getTopMoviesFromInternet(callback)
    }

    private fun checkResponse(serverResponse: MovieData): AppState {
        return if (serverResponse == null || serverResponse.movieInfo.isNullOrEmpty()) {
            AppState.Error(Throwable(CORRUPTED_DATA))
        } else {
            AppState.Success(serverResponse)
        }
    }

    fun addFilmToWatchLater(movie: MovieInfo) {
        Thread {
            localRepository.saveFilmToDB(WatchLaterFilmEntity(0, movie.title
                    ?: "Error", SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date())))
        }.start()
    }

}


