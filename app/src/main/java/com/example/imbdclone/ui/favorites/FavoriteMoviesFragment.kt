package com.example.imbdclone.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imbdclone.MyApp
import com.example.imbdclone.databinding.FavoriteMoviesFragmentBinding
import com.example.imbdclone.di.MovieViewModelFactory
import com.example.imbdclone.ui.adapters.FavoriteMoviesAdapter

class FavoriteMoviesFragment: Fragment() {

    private val viewModel: FavoriteMoviesViewModel by viewModels {
        val app = requireActivity().application as MyApp
        MovieViewModelFactory(app.repository)
    }

    private lateinit var binding: FavoriteMoviesFragmentBinding

    private lateinit var favoriteMoviesAdapter: FavoriteMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FavoriteMoviesFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.favorites.layoutManager = layoutManager

        favoriteMoviesAdapter = FavoriteMoviesAdapter { movie ->
            viewModel.removeFromFavorites(movie)
        }
        binding.favorites.adapter = favoriteMoviesAdapter

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is FavoriteMoviesUiState.Loading -> binding.progressBar2.visibility = View.VISIBLE

                is FavoriteMoviesUiState.Success -> {
                    favoriteMoviesAdapter.submitList(state.favoriteMoviesList.toList())
                    showContent()
                }

                is FavoriteMoviesUiState.Error -> {
                    hideContent()
                }
            }
        }
    }

    private fun showContent() {
        binding.progressBar2.visibility = View.GONE
        binding.mainFavoritesView.visibility = View.VISIBLE
        binding.fallbackContainer.visibility = View.GONE
    }

    private fun hideContent() {
        binding.mainFavoritesView.visibility = View.GONE
        binding.progressBar2.visibility = View.GONE
        binding.fallbackContainer.visibility = View.VISIBLE
    }
}