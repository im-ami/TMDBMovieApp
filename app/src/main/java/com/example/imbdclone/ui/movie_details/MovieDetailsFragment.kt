package com.example.imbdclone.ui.movie_details

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imbdclone.di.MovieViewModelFactory
import com.example.imbdclone.MyApp
import com.example.imbdclone.R
import com.example.imbdclone.ui.adapters.CastListAdapter
import com.example.imbdclone.ui.adapters.MovieImagesListAdapter
import com.example.imbdclone.data.model.Cast
import com.example.imbdclone.ui.favorites.FavoriteMoviesEvent
import com.example.imbdclone.data.model.MovieDetails
import com.example.imbdclone.data.model.MoviePosters
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Locale

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {
    private lateinit var progressBar: ProgressBar
    private lateinit var backgroundPoster: ImageView
    private lateinit var mainPoster: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var ratings: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var tagline: TextView
    private lateinit var overview: TextView
    private lateinit var castList: RecyclerView
    private lateinit var favoriteButton: View
    private lateinit var mainDetailedView: ConstraintLayout
    private lateinit var fallbackContainer: LinearLayout
    private lateinit var movieImageList: RecyclerView
    private lateinit var castListAdapter: CastListAdapter
    private lateinit var movieImageListAdapter: MovieImagesListAdapter
    private lateinit var bottomNavBar: BottomNavigationView
    private var isExpanded = false

    private val viewModel: MovieDetailsViewModel by viewModels {
        val app = requireActivity().application as MyApp
        MovieViewModelFactory(app.repository)
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

        progressBar = view.findViewById(R.id.progressBar3)
        backgroundPoster = view.findViewById(R.id.backdrop)
        mainPoster = view.findViewById(R.id.poster)
        movieTitle = view.findViewById(R.id.full_title)
        ratings = view.findViewById(R.id.rating_number)
        ratingBar = view.findViewById(R.id.rating_bar)
        releaseDate = view.findViewById(R.id.release_year)
        tagline = view.findViewById(R.id.tagline)
        overview = view.findViewById(R.id.synopsis)
        castList = view.findViewById(R.id.cast_list)
        movieImageList = view.findViewById(R.id.movie_images_list)
        mainDetailedView = view.findViewById(R.id.main_detailed_view)
        fallbackContainer = view.findViewById(R.id.fallback_container)
        favoriteButton = view.findViewById(R.id.fab)

        bottomNavBar = requireActivity().findViewById(R.id.bottom_nav_bar)
        bottomNavBar.visibility = View.GONE

        val movieID = arguments?.getInt(MOVIE_ID)

        if (movieID != null) {
            viewModel.loadMovieDetails(movieID)
            
            viewModel.uiState.observe(viewLifecycleOwner) { state ->
                when(state) {
                    is MovieDetailsViewModel.MovieDetailsUiState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }

                    is MovieDetailsViewModel.MovieDetailsUiState.Success -> {
                        showContent()

                        val details = state.movie
                        val credits = state.credits
                        val images = state.movieImages

                        setMovieDetails(view, details)
                        setMovieCredits(credits)
                        setMovieImages(images)
                    }

                    is MovieDetailsViewModel.MovieDetailsUiState.Error -> {
                        hideContent()
                    }
                }
            }

            viewModel.isMovieFavorite(movieID).observe(viewLifecycleOwner) { isFavorite ->
                favoriteButton.setOnClickListener {
                    if (isFavorite) {
                        viewModel.onEvent(FavoriteMoviesEvent.RemoveFromFavorites(movieId = movieID))
                        Toast.makeText(requireContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.onEvent(FavoriteMoviesEvent.AddToFavorites(movieId = movieID))
                        Toast.makeText(requireContext(), "Added to Favorites", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        overview.setOnClickListener {
            isExpanded = !isExpanded
            overview.maxLines = if (isExpanded) Int.MAX_VALUE else 2
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomNavBar.visibility = View.VISIBLE
    }

    private fun showContent() {
        progressBar.visibility = View.GONE
        mainDetailedView.visibility = View.VISIBLE
        fallbackContainer.visibility = View.GONE
    }

    private fun hideContent() {
        mainDetailedView.visibility = View.GONE
        progressBar.visibility = View.GONE
        fallbackContainer.visibility = View.VISIBLE
    }

    private fun setMovieDetails(view: View, details: MovieDetails) {
        val backgroundImageUrl = "https://image.tmdb.org/t/p/original${details.backdrop_path}"
        val posterImageUrl = "https://image.tmdb.org/t/p/original${details.posterPath}"

        Glide.with(view).load(backgroundImageUrl).into(backgroundPoster)
        Glide.with(view).load(posterImageUrl).into(mainPoster)
        movieTitle.text = details.title
        ratings.text = String.format(Locale.US,"%.1f", details.vote_average)
        ratingBar.rating = details.vote_average.toFloat()
        releaseDate.text = details.release_date
        tagline.text = details.tagline
        overview.text = details.overview
    }

    private fun setMovieCredits(credits: List<Cast>) {
        castList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        castListAdapter = CastListAdapter(credits)
        castList.adapter = castListAdapter
    }

    private fun setMovieImages(images: List<MoviePosters>) {
        movieImageList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        movieImageListAdapter = MovieImagesListAdapter(images)
        movieImageList.adapter = movieImageListAdapter
    }
}