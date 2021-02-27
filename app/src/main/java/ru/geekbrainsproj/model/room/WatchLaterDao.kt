package ru.geekbrainsproj.model.room

import androidx.room.*

@Dao
interface WatchLaterDao {

    @Query("SELECT * FROM WatchLaterFilmEntity")
    fun all(): List<WatchLaterFilmEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: WatchLaterFilmEntity)

    @Delete
    fun delete(entity: WatchLaterFilmEntity)

}