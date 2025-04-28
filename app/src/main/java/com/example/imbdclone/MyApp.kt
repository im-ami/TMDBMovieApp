package com.example.imbdclone

import android.app.Application
import androidx.room.Room
import com.example.imbdclone.data.repository.CentralRepository
import com.example.imbdclone.data.source.local.FavoritesDB
import com.example.imbdclone.data.source.remote.TMDBApiService
import com.example.imbdclone.usecase.FavoritesUseCase

class MyApp : Application() {

    lateinit var repository: CentralRepository
        private set

    lateinit var favoritesUseCase: FavoritesUseCase
        private set

    override fun onCreate() {
        super.onCreate()

        val api = TMDBApiService.create()

        val db = Room.databaseBuilder(
            applicationContext,
            FavoritesDB::class.java,
            "movies-db"
        ).build()
        val dao = db.dao

        repository = CentralRepository(api, dao)
        favoritesUseCase = FavoritesUseCase(repository)
    }
}



