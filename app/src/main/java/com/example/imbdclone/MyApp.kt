package com.example.imbdclone

import android.app.Application
import androidx.room.Room
import com.example.imbdclone.data.repository.CentralRepositoryImpl
import com.example.imbdclone.data.repository.LocalRepository
import com.example.imbdclone.data.repository.RemoteRepository
import com.example.imbdclone.data.source.local.FavoritesDB
import com.example.imbdclone.data.source.remote.TMDBApiService

class MyApp : Application() {

    lateinit var repository: CentralRepositoryImpl
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

        val localRepository = LocalRepository(dao)
        val remoteRepository = RemoteRepository(api)

        repository = CentralRepositoryImpl(localRepository, remoteRepository)
    }
}



