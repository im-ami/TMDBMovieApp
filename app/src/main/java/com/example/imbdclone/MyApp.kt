package com.example.imbdclone

import android.app.Application
import androidx.room.Room
import com.example.imbdclone.data.repository.MoviesRepository
import com.example.imbdclone.data.source.local.FavoritesDB
import com.example.imbdclone.data.source.remote.TMDBApiService

class MyApp : Application() {

    lateinit var repository: MoviesRepository

    private set
    override fun onCreate() {
        super.onCreate()

        val api = TMDBApiService.create()

        val db = Room.databaseBuilder(
            applicationContext,
            FavoritesDB::class.java,
            "movies-db"
        )
            .fallbackToDestructiveMigration(false)
            .build()

        val dao = db.dao

        repository = MoviesRepository(api, dao)
    }
}
