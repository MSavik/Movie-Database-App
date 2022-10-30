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
import com.msavik.movie_database_app.databinding.FragmentTopRatedBinding
import com.msavik.movie_database_app.ui.home.movie.HomeFragment
import com.msavik.movie_database_app.ui.home.movie.MovieAdapter
import com.msavik.movie_database_app.ui.home.movie.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TopRatedFragment : Fragment(R.layout.fragment_top_rated) {

    private lateinit var binding: FragmentTopRatedBinding
    private lateinit var homeFragment: HomeFragment
    private val viewModel: MovieViewModel by sharedViewModel()
    private val movieAdapter = MovieAdapter()
    private var dataTable: List<Movie> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTopRatedBinding.bind(view)
        homeFragment = requireParentFragment() as HomeFragment


        println("PRE GET TOP")
//        viewModel.getTopRatedMoviesList()
        homeFragment.dataTable = viewModel.topRatedMovieListLiveData.value?.data ?: emptyList()
        println("VREDNOST U movieListLiveData: ${viewModel.topRatedMovieListLiveData.value?.data}")
        println("POSLE GET TOP")

        binding.rvTopRated.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false)
            adapter = movieAdapter
        }
        movieAdapter.submitList(homeFragment.dataTable)
        movieAdapter.notifyDataSetChanged()
//        println("VREDNOST 2: ${homeFragment.dataTable[1]}")
    }
}