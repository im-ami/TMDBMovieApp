package com.example.imbdclone

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.imbdclone.data.MovieData
import java.util.Locale

class MoviesAdapter(
    private var moviesList: List<MovieData>
): RecyclerView.Adapter<MoviesAdapter.LatestMoviesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestMoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        return LatestMoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: LatestMoviesViewHolder, position: Int) {
        val item = moviesList[position]

        val imageUrl = "https://image.tmdb.org/t/p/w500" + item.posterPath
        Log.d("GlideDebug", "Poster path: $imageUrl")

        GlideApp.with(holder.itemView)
            .load(imageUrl)
            .into(holder.moviePoster)

        holder.movieTitle.text = item.title
        holder.movieRatings.text = String.format(Locale.US,"%.1f", item.vote_average)
    }

    fun updateMoviesList(newMoviesList: List<MovieData>) {
        moviesList = newMoviesList
        notifyDataSetChanged()
    }

    class LatestMoviesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var moviePoster: ImageView = itemView.findViewById(R.id.movie_poster)
        val movieTitle: TextView = itemView.findViewById(R.id.movie_title)
        val movieRatings: TextView = itemView.findViewById(R.id.movie_ratings)
    }
}