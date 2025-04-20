package com.example.imbdclone.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.imbdclone.GlideApp
import com.example.imbdclone.R
import com.example.imbdclone.data.model.Cast
import com.google.android.material.imageview.ShapeableImageView

class CastListAdapter(
    private val castList: List<Cast>
): RecyclerView.Adapter<CastListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.horizontal_scroll, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(castList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profilePhoto: ShapeableImageView = itemView.findViewById(R.id.cast_profile_photo)
        private val actorName: TextView = itemView.findViewById(R.id.cast_name)
        private val characterName: TextView = itemView.findViewById(R.id.character_name)

        fun bind(item: Cast) {
            val actorsImageUrl = "https://image.tmdb.org/t/p/w500" + item.profile_path

            GlideApp.with(itemView)
                .load(actorsImageUrl)
                .into(profilePhoto)

            actorName.text = item.name
            characterName.text = item.character
        }
    }
}