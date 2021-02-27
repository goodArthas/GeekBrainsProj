package ru.geekbrainsproj.view_model

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrainsproj.App
import ru.geekbrainsproj.model.repositories.locale.LocalRepository
import ru.geekbrainsproj.model.repositories.locale.LocalRepositoryImpl
import ru.geekbrainsproj.model.room.WatchLaterFilmEntity

class WatchLaterViewModel(private val localRepository: LocalRepository = LocalRepositoryImpl(App.getWatchLaterDao())) : ViewModel(), LifecycleObserver {

    val liveData: MutableLiveData<List<WatchLaterFilmEntity>> = MutableLiveData()

    init {
        onCreate()
    }

    private fun onCreate() {
        getDataFromLocal()
    }

    private fun getDataFromLocal() {
        Thread {
            liveData.postValue(localRepository.getAll())
        }.start()
    }


}