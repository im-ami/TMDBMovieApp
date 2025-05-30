package com.example.imbdclone.ui.favorites

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imbdclone.MyApp
import com.example.imbdclone.R
import com.example.imbdclone.di.MovieViewModelFactory
import com.example.imbdclone.ui.adapters.FavoriteMoviesAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FavoriteMoviesFragment: Fragment(R.layout.favorite_movies_fragment) {
    private lateinit var favoriteMoviesView: ConstraintLayout
    private lateinit var favoritesList: RecyclerView
    private lateinit var favoriteMoviesAdapter: FavoriteMoviesAdapter
    private lateinit var progressBar: ProgressBar

    private val viewModel: FavoriteMoviesViewModel by viewModels {
        val app = requireActivity().application as MyApp
        MovieViewModelFactory(app.repository, app.favoritesUseCase)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.progressBar2)
        favoriteMoviesView = view.findViewById(R.id.main_favorites_view)
        favoritesList = view.findViewById(R.id.favorites)

        val layoutManager = GridLayoutManager(requireContext(), 2)
        favoritesList.layoutManager = layoutManager

        favoriteMoviesAdapter = FavoriteMoviesAdapter { movie ->
            viewModel.removeFromFavorites(movie)
        }
        favoritesList.adapter = favoriteMoviesAdapter

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when(state) {
                FavoriteMoviesViewModel.FavoriteMoviesUiState.Loading -> progressBar.visibility = View.VISIBLE

                is FavoriteMoviesViewModel.FavoriteMoviesUiState.Success -> {
                    progressBar.visibility = View.GONE

                    favoriteMoviesAdapter.submitList(state.favoriteMoviesList)
                    showContent()
                }

                is FavoriteMoviesViewModel.FavoriteMoviesUiState.Error -> {
                    hideContent()
                }
            }
        }
    }


    private fun showContent() {
        progressBar.visibility = View.GONE
        favoriteMoviesView.visibility = View.VISIBLE
    }

    private fun hideContent() {
        favoriteMoviesView.visibility = View.GONE
        progressBar.visibility = View.GONE
    }
}