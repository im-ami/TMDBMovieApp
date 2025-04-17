package com.example.imbdclone.movie_details

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.imbdclone.GlideApp
import com.example.imbdclone.MovieViewModelFactory
import com.example.imbdclone.R
import com.example.imbdclone.data.repository.MoviesRepository

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {
    private lateinit var backgroundPoster: ImageView
    private val viewModel: MovieDetailsViewModel by viewModels {
        MovieViewModelFactory(MoviesRepository())
    }

    companion object {
        private const val MOVIE_ID = "movie_id"

        fun newInstance(movieID: Int): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
            val bundle = Bundle()
            bundle.putInt(MOVIE_ID, movieID)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backgroundPoster = view.findViewById(R.id.backdrop)
        val movieID = arguments?.getInt(MOVIE_ID)
        if (movieID != null) {
            viewModel.getBackdropPath(movieID)
            viewModel.backdropPath.observe(viewLifecycleOwner) { path ->
                val imageUrl = "https://image.tmdb.org/t/p/original$path"

                GlideApp.with(view)
                    .load(imageUrl)
                    .into(backgroundPoster)
            }
        }
    }
}