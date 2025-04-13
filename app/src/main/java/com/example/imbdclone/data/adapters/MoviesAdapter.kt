package com.example.imbdclone.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imbdclone.GlideApp
import com.example.imbdclone.R
import com.example.imbdclone.data.model.MovieData
import java.util.Locale

val diffUtil = object : DiffUtil.ItemCallback<MovieData>() {
    override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
        return oldItem == newItem
    }
}

class MoviesAdapter : ListAdapter<MovieData, MoviesAdapter.LatestMoviesViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestMoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        return LatestMoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: LatestMoviesViewHolder, position: Int) {
        val item = getItem(position)

        val imageUrl = "https://image.tmdb.org/t/p/w500" + item.posterPath
        GlideApp.with(holder.itemView)
            .load(imageUrl)
            .into(holder.moviePoster)

        holder.movieTitle.text = item.title
        holder.movieRatings.text = String.format(Locale.US,"%.1f", item.vote_average)
    }

    class LatestMoviesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val moviePoster: ImageView = itemView.findViewById(R.id.movie_poster)
        val movieTitle: TextView = itemView.findViewById(R.id.movie_title)
        val movieRatings: TextView = itemView.findViewById(R.id.movie_ratings)
    }
}
