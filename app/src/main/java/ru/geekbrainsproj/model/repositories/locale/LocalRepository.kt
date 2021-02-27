package ru.geekbrainsproj.model.repositories.locale

import ru.geekbrainsproj.model.room.WatchLaterFilmEntity

interface LocalRepository {

    fun getAll(): List<WatchLaterFilmEntity>
    fun saveFilmToDB(entity: WatchLaterFilmEntity)

}