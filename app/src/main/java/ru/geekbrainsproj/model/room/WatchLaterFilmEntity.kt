package ru.geekbrainsproj.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class WatchLaterFilmEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        val nameFilm: String,
        val data: String
)