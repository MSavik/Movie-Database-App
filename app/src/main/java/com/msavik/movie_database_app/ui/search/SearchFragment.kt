package com.msavik.movie_database_app.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.msavik.domain.model.movie.Movie
import com.msavik.domain.utility.Page
import com.msavik.domain.utility.Resource
import com.msavik.movie_database_app.R
import com.msavik.movie_database_app.databinding.FragmentSearchBinding
import com.msavik.movie_database_app.ui.home.MovieAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by sharedViewModel()
    private val searchMovieAdapter = MovieAdapter { movieId ->
        onItemClick(movieId)
    }
    private var searchMovieList: List<Movie> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        initObserver()
        binding.srlSearch.setOnRefreshListener {
            viewModel.searchMovies(binding.svSearch.query.toString())
        }
        initView()
        setMovieFilter()
    }

    private fun initObserver() {
        viewModel.searchMoviesListLiveData.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Loading -> {
                    binding.srlSearch.isRefreshing = true
                    Log.d(TAG, "Loading...")
                }
                is Resource.Success -> {
                    response.data?.let { movieList ->
                        binding.srlSearch.isRefreshing = false
                        Log.d(TAG, "Success!")
                        searchMovieList = movieList

                        Log.d(TAG, "LOGOBSERVER: $searchMovieList")

                        searchMovieAdapter.submitList(searchMovieList)
                        searchMovieAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        binding.srlSearch.isRefreshing = false
                        Log.e(TAG, "Error: $message")
                        Snackbar.make(requireView(), "Error: $message", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun initView() {
        binding.rvSearch.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false)
            adapter = searchMovieAdapter
        }
    }

    private fun setMovieFilter() {
        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchMovies(query ?: "")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun onItemClick(movieId: Int) {
        val bundle = bundleOf(
            "movieId" to movieId.toString(),
            "page" to Page.SEARCH.name
        )
        findNavController().navigate(R.id.detailsFragment, bundle)
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName
    }
}