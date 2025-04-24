package com.example.imbdclone

import android.app.Application
import androidx.room.Room
import com.example.imbdclone.data.repository.LocalRepository
import com.example.imbdclone.data.repository.MoviesRepository
import com.example.imbdclone.data.source.local.FavoritesDB
import com.example.imbdclone.data.source.remote.TMDBApiService
import com.example.imbdclone.usecase.FavoritesUseCase

class MyApp : Application() {

    lateinit var repository: MoviesRepository
    lateinit var localRepository: LocalRepository
    lateinit var favoritesUseCase: FavoritesUseCase

    private set
    override fun onCreate() {
        super.onCreate()

        val api = TMDBApiService.create()
        repository = MoviesRepository(api)

        val db = Room.databaseBuilder(
            applicationContext,
            FavoritesDB::class.java,
            "movies-db"
        ).build()
        val dao = db.dao
        localRepository = LocalRepository(dao)

        favoritesUseCase = FavoritesUseCase(localRepository)
    }
}

