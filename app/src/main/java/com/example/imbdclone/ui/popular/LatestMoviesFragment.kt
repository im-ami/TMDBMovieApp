package com.example.imbdclone.ui.popular

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imbdclone.di.MovieViewModelFactory
import com.example.imbdclone.MyApp
import com.example.imbdclone.R
import com.example.imbdclone.ui.adapters.MoviesAdapter
import com.example.imbdclone.ui.movie_details.MovieDetailsFragment

class LatestMoviesFragment : Fragment(R.layout.latest_movies_fragment) {

    private lateinit var latestMoviesList: RecyclerView
    private lateinit var latestMoviesAdapter: MoviesAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var latestMoviesView: ConstraintLayout
    private lateinit var fallbackContainer: LinearLayout

    private val viewModel: LatestMoviesViewModel by viewModels {
        val app = requireActivity().application as MyApp
        MovieViewModelFactory(app.repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        latestMoviesView = view.findViewById(R.id.main_latest_view)
        progressBar = view.findViewById(R.id.progressBar)
        fallbackContainer = view.findViewById(R.id.fallback_container)
        latestMoviesList = view.findViewById(R.id.latest)

        val layoutManager = GridLayoutManager(requireContext(), 2)
        latestMoviesList.layoutManager = layoutManager

        latestMoviesAdapter = MoviesAdapter { movie ->
            val movieID = movie.id

            val fragment = MovieDetailsFragment.newInstance(movieID)
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .addToBackStack(null)
                .commit()
        }

        latestMoviesList.adapter = latestMoviesAdapter

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is LatestMoviesViewModel.LatestMoviesUiState.Loading -> progressBar.visibility = View.VISIBLE

                is LatestMoviesViewModel.LatestMoviesUiState.Success -> {
                    progressBar.visibility = View.GONE

                    latestMoviesAdapter.submitList(state.result)
                    showContent()
                }

                is LatestMoviesViewModel.LatestMoviesUiState.Error -> hideContent()
            }
        }

        latestMoviesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val visibleThreshold = 4

                if (totalItemCount <= lastVisibleItem + visibleThreshold) {
                    viewModel.loadNextPage()
                }
            }
        })
    }

    private fun showContent() {
        progressBar.visibility = View.GONE
        latestMoviesView.visibility = View.VISIBLE
        fallbackContainer.visibility = View.GONE
    }

    private fun hideContent() {
        latestMoviesView.visibility = View.GONE
        progressBar.visibility = View.GONE
        fallbackContainer.visibility = View.VISIBLE
    }
}