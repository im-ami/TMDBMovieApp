package com.example.imbdclone.movie_details

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imbdclone.GlideApp
import com.example.imbdclone.MovieViewModelFactory
import com.example.imbdclone.R
import com.example.imbdclone.data.adapters.CastListAdapter
import com.example.imbdclone.data.adapters.MovieImagesListAdapter
import com.example.imbdclone.data.repository.MoviesRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Locale

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {
    private lateinit var backgroundPoster: ImageView
    private lateinit var mainPoster: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var ratings: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var tagline: TextView
    private lateinit var overview: TextView
    private lateinit var castList: RecyclerView
    private lateinit var movieImageList: RecyclerView
    private lateinit var castListAdapter: CastListAdapter
    private lateinit var movieImageListAdapter: MovieImagesListAdapter
    private lateinit var bottomNavBar: BottomNavigationView
    private var isExpanded = false

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
        mainPoster = view.findViewById(R.id.poster)
        movieTitle = view.findViewById(R.id.full_title)
        ratings = view.findViewById(R.id.rating_number)
        ratingBar = view.findViewById(R.id.rating_bar)
        releaseDate = view.findViewById(R.id.release_year)
        tagline = view.findViewById(R.id.tagline)
        overview = view.findViewById(R.id.synopsis)
        castList = view.findViewById(R.id.cast_list)
        movieImageList = view.findViewById(R.id.movie_images_list)

        bottomNavBar = requireActivity().findViewById(R.id.bottom_nav_bar)
        bottomNavBar.visibility = View.GONE

        val movieID = arguments?.getInt(MOVIE_ID)

        if (movieID != null) {
            viewModel.getMovieData(movieID)
            viewModel.movieData.observe(viewLifecycleOwner) { details ->
                val backgroundImageUrl = "https://image.tmdb.org/t/p/original${details.backdrop_path}"
                val posterImageUrl = "https://image.tmdb.org/t/p/original${details.posterPath}"

                GlideApp.with(view).load(backgroundImageUrl).into(backgroundPoster)
                GlideApp.with(view).load(posterImageUrl).into(mainPoster)
                movieTitle.text = details.title
                ratings.text = String.format(Locale.US,"%.1f", details.vote_average)
                ratingBar.rating = details.vote_average.toFloat()
                releaseDate.text = details.release_date
                tagline.text = details.tagline
                overview.text = details.overview
            }

            viewModel.getMovieCredits(movieID)
            viewModel.movieCredits.observe(viewLifecycleOwner) { cast ->
                castList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                castListAdapter = CastListAdapter(cast)
                castList.adapter = castListAdapter
            }

            viewModel.getMovieImages(movieID)
            viewModel.movieImages.observe(viewLifecycleOwner) { images ->
                movieImageList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                movieImageListAdapter = MovieImagesListAdapter(images)
                movieImageList.adapter = movieImageListAdapter
            }

            overview.setOnClickListener {
                isExpanded = !isExpanded
                overview.maxLines = if (isExpanded) Int.MAX_VALUE else 2
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomNavBar.visibility = View.VISIBLE
    }
}