package com.example.imbdclone.ui.latest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imbdclone.MyApp
import com.example.imbdclone.R
import com.example.imbdclone.data.model.FavoriteMovies
import com.example.imbdclone.databinding.LatestMoviesFragmentBinding
import com.example.imbdclone.di.MovieViewModelFactory
import com.example.imbdclone.ui.adapters.MoviesAdapter
import com.example.imbdclone.ui.movie_details.MovieDetailsFragment

class LatestMoviesFragment : Fragment() {

    private val viewModel: LatestMoviesViewModel by viewModels {
        val app = requireActivity().application as MyApp
        MovieViewModelFactory(app.repository, app.favoritesUseCase)
    }

    private lateinit var binding: LatestMoviesFragmentBinding

    private lateinit var latestMoviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = LatestMoviesFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.latest.layoutManager = layoutManager

        latestMoviesAdapter = MoviesAdapter (
            onItemClick = { movie ->
                val movieID = movie.id
                val isFavorite = movie.isFavorite
                val posterPath = movie.posterPath
                val movieTitle = movie.title
                val voteAverage = movie.voteAverage

                val fragment = MovieDetailsFragment.newInstance(
                    movieID,
                    isFavorite,
                    posterPath,
                    movieTitle,
                    voteAverage
                )
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .addToBackStack(null)
                    .commit()
            },
            onFavoriteClick = { movie ->
                val favoriteMovie = FavoriteMovies(movie.id, movie.isFavorite, movie.title, movie.posterPath, movie.voteAverage)
                if (movie.isFavorite) {
                    viewModel.removeFromFavorites(favoriteMovie)
                } else {
                    viewModel.addToFavorites(favoriteMovie)
                }
        })

        binding.latest.adapter = latestMoviesAdapter

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is LatestMoviesViewModel.LatestMoviesUiState.Loading -> binding.progressBar.visibility = View.VISIBLE

                is LatestMoviesViewModel.LatestMoviesUiState.Success -> {
                    binding.progressBar.visibility = View.GONE

                    latestMoviesAdapter.submitList(state.result)
                    showContent()
                }
                is LatestMoviesViewModel.LatestMoviesUiState.Error -> hideContent()
            }
        }

        binding.latest.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
        binding.progressBar.visibility = View.GONE
        binding.mainLatestView.visibility = View.VISIBLE
        binding.fallbackContainer.visibility = View.GONE
    }

    private fun hideContent() {
        binding.mainLatestView.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.fallbackContainer.visibility = View.VISIBLE
    }
}