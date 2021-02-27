package ru.geekbrainsproj.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WatchLaterFilmEntity::class], version = 2, exportSchema = false)
abstract class WatchLaterDataBase : RoomDatabase() {

    abstract fun watchLaterDao(): WatchLaterDao

}