package com.example.imbdclone.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imbdclone.R
import com.example.imbdclone.data.model.MoviePosters
import com.google.android.material.imageview.ShapeableImageView

class MovieImagesListAdapter(
    private val movieImageList: List<MoviePosters>
): RecyclerView.Adapter<MovieImagesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_stills_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieImageList[position])
    }

    override fun getItemCount(): Int {
        return movieImageList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val movieImage: ShapeableImageView = itemView.findViewById(R.id.movie_image)

        fun bind(item: MoviePosters) {
            val movieImageUrl = "https://image.tmdb.org/t/p/w500" + item.filePath

            Glide.with(itemView)
                .load(movieImageUrl)
                .into(movieImage)
        }
    }
}