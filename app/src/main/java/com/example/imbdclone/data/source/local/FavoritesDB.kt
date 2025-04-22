package com.example.imbdclone.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.imbdclone.data.model.FavoriteMovies

@Database(
    entities = [FavoriteMovies::class],
    version = 2,
    exportSchema = false
)
abstract class FavoritesDB: RoomDatabase() {
    abstract val dao: FavoriteMoviesDao
}

