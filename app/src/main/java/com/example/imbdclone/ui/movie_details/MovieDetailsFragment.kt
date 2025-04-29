package com.example.imbdclone.ui.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.imbdclone.MyApp
import com.example.imbdclone.R
import com.example.imbdclone.data.model.Cast
import com.example.imbdclone.data.model.FavoriteMovies
import com.example.imbdclone.data.model.MovieDetails
import com.example.imbdclone.data.model.MoviePosters
import com.example.imbdclone.databinding.MovieDetailsFragmentBinding
import com.example.imbdclone.di.MovieViewModelFactory
import com.example.imbdclone.ui.adapters.CastListAdapter
import com.example.imbdclone.ui.adapters.MovieImagesListAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Locale

class MovieDetailsFragment : Fragment() {

    private val viewModel: MovieDetailsViewModel by viewModels {
        val app = requireActivity().application as MyApp
        MovieViewModelFactory(app.repository)
    }

    private lateinit var binding: MovieDetailsFragmentBinding

    private lateinit var castListAdapter: CastListAdapter
    private lateinit var movieImageListAdapter: MovieImagesListAdapter
    private lateinit var bottomNavBar: BottomNavigationView

    private var isExpanded = false

    companion object {
        private const val MOVIE_ID = "movie_id"
        private const val IS_FAVORITE = "is_favorite"
        private const val POSTER_PATH = "poster_path"
        private const val TITLE = "title"
        private const val VOTE_AVERAGE = "vote_average"

        fun newInstance(movieId: Int, isFavorite: Boolean, posterPath: String?, title: String, voteAverage: Double): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
            val bundle = Bundle()
            bundle.putInt(MOVIE_ID, movieId)
            bundle.putBoolean(IS_FAVORITE, isFavorite)
            bundle.putString(POSTER_PATH, posterPath)
            bundle.putString(TITLE, title)
            bundle.putDouble(VOTE_AVERAGE, voteAverage)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = MovieDetailsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavBar = requireActivity().findViewById(R.id.bottom_nav_bar)
        bottomNavBar.visibility = View.GONE

        val movieID = requireArguments().getInt(MOVIE_ID)
        var isFavorite = requireArguments().getBoolean(IS_FAVORITE)

        viewModel.loadMovieDetails(movieID)
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is MovieDetailsViewModel.MovieDetailsUiState.Loading -> {
                    binding.progressBar3.visibility = View.VISIBLE
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

        val posterPath = requireArguments().getString(POSTER_PATH)
        val title = requireArguments().getString(TITLE)!!
        val voteAverage = requireArguments().getDouble(VOTE_AVERAGE)

        binding.fab.setOnClickListener {
            val details = FavoriteMovies(movieID, isFavorite, title, posterPath, voteAverage)

            if (!isFavorite) {
                isFavorite = true
                binding.fab.setImageResource(R.drawable.heart)
                Toast.makeText(requireContext(), "Added To Favorites", Toast.LENGTH_SHORT).show()
                viewModel.addToFavorites(details)
            } else {
                isFavorite = false
                binding.fab.setImageResource(R.drawable.empty_heart)
                Toast.makeText(requireContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show()
                viewModel.removeFromFavorites(details)
            }
        }

        binding.synopsis.setOnClickListener {
            isExpanded = !isExpanded
            binding.synopsis.maxLines = if (isExpanded) Int.MAX_VALUE else 2
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomNavBar.visibility = View.VISIBLE
    }

    private fun showContent() {
        binding.progressBar3.visibility = View.GONE
        binding.mainDetailedView.visibility = View.VISIBLE
        binding.fallbackContainer.visibility = View.GONE
    }

    private fun hideContent() {
        binding.mainDetailedView.visibility = View.GONE
        binding.progressBar3.visibility = View.GONE
        binding.fallbackContainer.visibility = View.VISIBLE
    }

    private fun setMovieDetails(view: View, details: MovieDetails) {
        val backgroundImageUrl = "https://image.tmdb.org/t/p/original${details.backdropPath}"
        val posterImageUrl = "https://image.tmdb.org/t/p/original${details.posterPath}"

        Glide.with(view).load(backgroundImageUrl).into(binding.backdrop)
        Glide.with(view).load(posterImageUrl).into(binding.poster)
        binding.fullTitle.text = details.title
        binding.ratingNumber.text = String.format(Locale.US,"%.1f", details.voteAverage)
        binding.ratingBar.rating = details.voteAverage.toFloat()
        binding.releaseYear.text = details.releaseDate
        binding.tagline.text = details.tagline
        binding.synopsis.text = details.overview

        viewModel.checkIfFavorites(details.id)
        viewModel.favoriteState.observe(viewLifecycleOwner) { favorite ->
            if (!favorite) {
                binding.fab.setImageResource(R.drawable.empty_heart)
            } else {
                binding.fab.setImageResource(R.drawable.heart)
            }
        }
    }

    private fun setMovieCredits(credits: List<Cast>) {
        val castList = binding.castList
        castList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        castListAdapter = CastListAdapter(credits)
        castList.adapter = castListAdapter
    }

    private fun setMovieImages(images: List<MoviePosters>) {
        val movieImageList = binding.movieImagesList
        movieImageList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        movieImageListAdapter = MovieImagesListAdapter(images)
        movieImageList.adapter = movieImageListAdapter
    }
}