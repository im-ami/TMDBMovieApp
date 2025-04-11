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
import com.example.imbdclone.MoviesAdapter
import com.example.imbdclone.R
import com.example.imbdclone.SharedViewModel
import com.example.imbdclone.data.MoviesRepository

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
        latestMoviesView.layoutManager = GridLayoutManager(requireContext(), 2)
        latestMoviesAdapter = MoviesAdapter(emptyList())
        latestMoviesView.adapter = latestMoviesAdapter

        viewModel.items.observe(viewLifecycleOwner) { moviesList ->
            latestMoviesAdapter.updateMoviesList(moviesList)
            progressBar.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.setTitle("Latest Movies")
    }
}