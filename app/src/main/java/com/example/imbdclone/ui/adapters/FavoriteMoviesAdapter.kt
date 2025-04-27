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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale

class FavoriteMoviesAdapter(
    private val onFavoriteClick: (FavoriteMovies) -> Unit
) : ListAdapter<FavoriteMovies, FavoriteMoviesAdapter.ViewHolder>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onFavoriteClick)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val moviePoster: ImageView = itemView.findViewById(R.id.movie_poster)
        private val movieTitle: TextView = itemView.findViewById(R.id.movie_title)
        private val movieRatings: TextView = itemView.findViewById(R.id.movie_ratings)
        private val likeButton: FloatingActionButton = itemView.findViewById(R.id.grid_fab)

        fun bind(item: FavoriteMovies, onFavoriteClick: (FavoriteMovies) -> Unit) {

            val imageUrl = "https://image.tmdb.org/t/p/w500" + item.backdrop_path
            Glide.with(itemView)
                .load(imageUrl)
                .into(moviePoster)

            movieTitle.text = item.title
            movieRatings.text = String.format(Locale.US, "%.1f", item.vote_average)
            likeButton.setImageResource(R.drawable.heart)

            likeButton.setOnClickListener {
                onFavoriteClick(item)
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<FavoriteMovies>() {
            override fun areItemsTheSame(oldItem: FavoriteMovies, newItem: FavoriteMovies): Boolean {
                return oldItem.movie_id == newItem.movie_id
            }

            override fun areContentsTheSame(oldItem: FavoriteMovies, newItem: FavoriteMovies): Boolean {
                return oldItem.movie_id == newItem.movie_id &&
                        oldItem.is_favorite == newItem.is_favorite &&
                        oldItem.title == newItem.title &&
                        oldItem.backdrop_path == newItem.backdrop_path &&
                        oldItem.vote_average == newItem.vote_average
            }
        }
    }
}