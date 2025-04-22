package com.example.imbdclone.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imbdclone.R
import com.example.imbdclone.data.model.FavoriteMovies
import java.util.Locale

class FavoriteMoviesAdapter :
    ListAdapter<FavoriteMovies, FavoriteMoviesAdapter.ViewHolder>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val moviePoster: ImageView = itemView.findViewById(R.id.movie_poster)
        private val movieTitle: TextView = itemView.findViewById(R.id.movie_title)
        private val movieRatings: TextView = itemView.findViewById(R.id.movie_ratings)

        fun bind(item: FavoriteMovies) {

            val imageUrl = "https://image.tmdb.org/t/p/w500" + item.backdrop_path
            Glide.with(itemView)
                .load(imageUrl)
                .into(moviePoster)

            movieTitle.text = item.title
            movieRatings.text = String.format(Locale.US, "%.1f", item.vote_average)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<FavoriteMovies>() {
            override fun areItemsTheSame(oldItem: FavoriteMovies, newItem: FavoriteMovies): Boolean {
                return oldItem.movie_id == newItem.movie_id
            }

            override fun areContentsTheSame(oldItem: FavoriteMovies, newItem: FavoriteMovies): Boolean {
                return oldItem == newItem
            }
        }
    }
}