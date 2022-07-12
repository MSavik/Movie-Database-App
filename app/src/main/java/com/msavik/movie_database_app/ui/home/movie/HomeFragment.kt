package com.msavik.movie_database_app.ui.home.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.msavik.domain.model.movie.Movie
import com.msavik.domain.utility.Resource
import com.msavik.movie_database_app.R
import com.msavik.movie_database_app.databinding.FragmentHomeBinding
import com.msavik.movie_database_app.ui.MainActivity

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: MovieViewModel
    private val movieAdapter = MovieAdapter()
    private var dataTable: List<Movie> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        viewModel = (activity as MainActivity).movieViewModel

        viewModel.movieList.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Loading -> {
                    Log.d(TAG, "Loading...")
                }
                is Resource.Success -> {
                    response.data?.let { movieList ->
                        Log.d(TAG, "Success!")
                        dataTable = movieList

                        binding.rv.apply {
                            layoutManager = LinearLayoutManager(
                                requireContext(),
                                RecyclerView.VERTICAL,
                                false)
                            adapter = movieAdapter
                        }
                        movieAdapter.submitList(dataTable)
                        movieAdapter.notifyDataSetChanged()
                        Log.d(TAG, "LOGOBSERVER: " + dataTable.toString())
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e(TAG, "Error: $message")
                        Snackbar.make(view, "Error: $message", Snackbar.LENGTH_LONG)
                    }
                }
            }
        }

        viewModel.getPopularMoviesList()
    }

    companion object {
        val TAG = this::class.java.simpleName
    }
}