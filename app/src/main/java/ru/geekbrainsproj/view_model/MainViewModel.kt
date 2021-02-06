package ru.geekbrainsproj.view_model


import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrainsproj.AppState
import ru.geekbrainsproj.model.Repository
import ru.geekbrainsproj.model.RepositoryImpl
import java.lang.Thread.sleep


class MainViewModel(private val repository: Repository = RepositoryImpl()) : ViewModel(), LifecycleObserver {

    private val liveData: MutableLiveData<AppState> = MutableLiveData()
    private val liveDataLoading: MutableLiveData<AppState.Loading> = MutableLiveData()

    fun getLiveData() = liveData
    fun getLiveDataLoading() = liveDataLoading

    init {
        onCreate()
    }

    private fun onCreate() {
        getDataFromLocale()
        Log.d("QWE", "onCreate: MainViewModel")
    }

    private fun getDataFromLocale() {

        Thread {
            liveDataLoading.postValue(AppState.Loading(true))
            sleep(1200)
            val random = (0..1).random()
            Log.d("QWE", "random: " + random)
            when (random) {
                0 -> {
                    liveData.postValue(AppState.Success(repository.getMoviesFromLocalStorage()))
                }
                else -> {
                    liveData.postValue(AppState.Error(NullPointerException()))
                }
            }

            liveDataLoading.postValue(AppState.Loading(false))

        }.start()


    }

}