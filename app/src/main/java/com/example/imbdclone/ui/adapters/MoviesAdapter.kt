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
import com.example.imbdclone.data.model.MovieData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale

class MoviesAdapter(
    private val onItemClick: (MovieData) -> Unit,
    private val onFavoriteClick: (MovieData) -> Unit
) : ListAdapter<MovieData, MoviesAdapter.LatestMoviesViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestMoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        return LatestMoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: LatestMoviesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClick, onFavoriteClick)
    }

    class LatestMoviesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val moviePoster: ImageView = itemView.findViewById(R.id.movie_poster)
        private val movieTitle: TextView = itemView.findViewById(R.id.movie_title)
        private val movieRatings: TextView = itemView.findViewById(R.id.movie_ratings)
        private val likeButton: FloatingActionButton = itemView.findViewById(R.id.grid_fab)

        fun bind(item: MovieData, onItemClick: (MovieData) -> Unit, onFavoriteClick: (MovieData) -> Unit)  {
            itemView.setOnClickListener {
                onItemClick(item)
            }

            val imageUrl = "https://image.tmdb.org/t/p/w500" + item.posterPath
            Glide.with(itemView)
                .load(imageUrl)
                .into(moviePoster)

            movieTitle.text = item.title
            movieRatings.text = String.format(Locale.US,"%.1f", item.vote_average)

            if (item.is_favorite) {
                likeButton.setImageResource(R.drawable.heart)
            } else {
                likeButton.setImageResource(R.drawable.empty_heart)
            }

            likeButton.setOnClickListener {
                onFavoriteClick(item)
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MovieData>() {
            override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
                return oldItem == newItem
            }
        }
    }
}
