package com.msavik.movie_database_app.ui.home.movie.page_fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.msavik.domain.model.movie.Movie
import com.msavik.domain.utility.Resource
import com.msavik.movie_database_app.R
import com.msavik.movie_database_app.databinding.FragmentPopularBinding
import com.msavik.movie_database_app.ui.home.movie.HomeFragment
import com.msavik.movie_database_app.ui.home.movie.MovieAdapter
import com.msavik.movie_database_app.ui.home.movie.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PopularFragment : Fragment(R.layout.fragment_popular) {

    private lateinit var binding: FragmentPopularBinding
    private lateinit var homeFragment: HomeFragment
    private val viewModel: MovieViewModel by sharedViewModel()
    private val movieAdapter = MovieAdapter()
    private var dataTable: List<Movie> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPopularBinding.bind(view)
        homeFragment = requireParentFragment() as HomeFragment


        println("PRE GET POPULAR")
//        viewModel.getPopularMoviesList()
        println("viewModel.movieListLD: ${viewModel.popularMovieListLiveData.value?.data}")
        homeFragment.dataTable = viewModel.popularMovieListLiveData.value?.data ?: emptyList()
        println("VREDNOST U movieListLiveData: ${viewModel.popularMovieListLiveData.value?.data}")
        dataTable = viewModel.popularMovieListLiveData.value?.data ?: emptyList()
        println("POSLE GET POPULAR")

        binding.rvPopular.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false)
            adapter = movieAdapter
        }
        movieAdapter.submitList(homeFragment.dataTable)
        movieAdapter.notifyDataSetChanged()
//        println("VREDNOST 1: ${homeFragment.dataTable[1]}")
    }
}