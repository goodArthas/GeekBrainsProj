package ru.geekbrainsproj.model.repositories.locale

import ru.geekbrainsproj.model.room.WatchLaterDao
import ru.geekbrainsproj.model.room.WatchLaterFilmEntity

class LocalRepositoryImpl(private val localeDataSource: WatchLaterDao) : LocalRepository {

    override fun getAll(): List<WatchLaterFilmEntity> {
        return localeDataSource.all()
    }

    override fun saveFilmToDB(entity: WatchLaterFilmEntity) {
        val b = localeDataSource.insert(entity)
    }

}