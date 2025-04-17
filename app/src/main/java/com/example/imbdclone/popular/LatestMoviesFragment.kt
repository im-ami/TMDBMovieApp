package com.example.imbdclone.popular

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imbdclone.MovieViewModelFactory
import com.example.imbdclone.data.adapters.MoviesAdapter
import com.example.imbdclone.R
import com.example.imbdclone.SharedViewModel
import com.example.imbdclone.data.repository.MoviesRepository
import com.example.imbdclone.movie_details.MovieDetailsFragment

class LatestMoviesFragment : Fragment(R.layout.latest_movies_fragment) {

    private lateinit var latestMoviesView: RecyclerView
    private lateinit var latestMoviesAdapter: MoviesAdapter
    private lateinit var progressBar: ProgressBar
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val viewModel: LatestMoviesViewModel by viewModels {
        MovieViewModelFactory(MoviesRepository())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.progressBar)

        latestMoviesView = view.findViewById(R.id.latest)
        val layoutManager = GridLayoutManager(requireContext(), 2)
        latestMoviesView.layoutManager = layoutManager

        latestMoviesAdapter = MoviesAdapter { movie ->
            val movieID = movie.id

            val fragment = MovieDetailsFragment.newInstance(movieID)
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .addToBackStack(null)
                .commit()
        }
        latestMoviesView.adapter = latestMoviesAdapter

        viewModel.items.observe(viewLifecycleOwner) { updatedList ->
            latestMoviesAdapter.submitList(updatedList)
            progressBar.visibility = View.GONE
        }

        latestMoviesView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

    override fun onResume() {
        super.onResume()
        sharedViewModel.setTitle("Latest Movies")
    }
}