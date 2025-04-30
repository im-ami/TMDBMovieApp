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
import com.example.imbdclone.databinding.LatestMoviesFragmentBinding
import com.example.imbdclone.di.MovieViewModelFactory
import com.example.imbdclone.ui.adapters.MoviesAdapter
import com.example.imbdclone.ui.movie_details.MovieDetailsFragment

class LatestMoviesFragment : Fragment() {

    private val viewModel: LatestMoviesViewModel by viewModels {
        val app = requireActivity().application as MyApp
        MovieViewModelFactory(app.repository)
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

        latestMoviesAdapter = MoviesAdapter(
            onItemClick = { movie ->
                viewModel.handleEvent(LatestMoviesEvent.LaunchDetailsPage(movie))
            },
            onFavoriteClick = { movie ->
                viewModel.handleEvent(LatestMoviesEvent.ToggleFavoriteButton(movie))
            }
        )
        binding.latest.adapter = latestMoviesAdapter

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is LatestMoviesUiState.Loading -> binding.progressBar.toggleVisibility(true)

                is LatestMoviesUiState.Success -> {
                    latestMoviesAdapter.submitList(state.result)
                    showContent()
                }
                is LatestMoviesUiState.Error -> hideContent()
            }
        }

        viewModel.navigateToDetails.observe(viewLifecycleOwner) { movie ->
            movie?.let {
                val fragment = MovieDetailsFragment.newInstance(
                    it.id, it.isFavorite, it.posterPath, it.title, it.voteAverage
                )
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .addToBackStack(null)
                    .commit()

                viewModel.clearNavigationEvent()
            }
        }


        binding.latest.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val visibleThreshold = 4

                if (totalItemCount <= lastVisibleItem + visibleThreshold) {
                    viewModel.handleEvent(LatestMoviesEvent.LoadNextResultSet)
                }
            }
        })
    }

    private fun View.toggleVisibility(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showContent() {
        binding.progressBar.toggleVisibility(false)
        binding.mainLatestView.toggleVisibility(true)
        binding.fallbackContainer.toggleVisibility(false)
    }

    private fun hideContent() {
        binding.progressBar.toggleVisibility(false)
        binding.mainLatestView.toggleVisibility(false)
        binding.fallbackContainer.toggleVisibility(true)
    }
}