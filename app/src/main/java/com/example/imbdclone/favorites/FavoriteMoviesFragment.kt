package com.example.imbdclone.favorites

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

class FavoriteMoviesFragment: Fragment(R.layout.favorite_movies_fragment) {
    private lateinit var favoriteMoviesView: RecyclerView
    private lateinit var favoriteMoviesAdapter: MoviesAdapter
    private lateinit var progressBar: ProgressBar
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val viewModel: FavoriteMoviesViewModel by viewModels {
        MovieViewModelFactory(MoviesRepository())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.progressBar2)

        favoriteMoviesView = view.findViewById(R.id.favorites)
        favoriteMoviesView.layoutManager = GridLayoutManager(requireContext(), 2)
        favoriteMoviesAdapter = MoviesAdapter(emptyList())
        favoriteMoviesView.adapter = favoriteMoviesAdapter

    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.setTitle("Favorite Movies")
    }
}