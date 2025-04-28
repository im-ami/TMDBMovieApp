package com.example.imbdclone.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.imbdclone.data.model.FavoriteMovies

@Dao
interface FavoriteMoviesDao {
    @Insert
    suspend fun addToFavorites(movie: FavoriteMovies)

    @Delete
    suspend fun removeFromFavorites(movie: FavoriteMovies)

    @Query("SELECT * FROM favoritemovies")
    fun getFavorites(): LiveData<List<FavoriteMovies>>

    @Query("SELECT EXISTS(SELECT 1 FROM FavoriteMovies WHERE movie_id = :movieId)")
    suspend fun isMovieFavorite(movieId: Int): Boolean

}