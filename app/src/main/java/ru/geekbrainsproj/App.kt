package ru.geekbrainsproj

import android.app.Application
import androidx.room.Room
import ru.geekbrainsproj.model.room.WatchLaterDao
import ru.geekbrainsproj.model.room.WatchLaterDataBase

class App : Application() {

    companion object {
        private var appInstance: App? = null
        private var db: WatchLaterDataBase? = null
        private const val DB_NAME = "WatchLater.db"

        fun getWatchLaterDao(): WatchLaterDao {
            if (db == null) {
                synchronized(WatchLaterDataBase::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                                appInstance!!.applicationContext,
                                WatchLaterDataBase::class.java,
                                DB_NAME)
                                .allowMainThreadQueries()
                                .build()
                    }
                }
            }

            return db!!.watchLaterDao()
        }

    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }


}
