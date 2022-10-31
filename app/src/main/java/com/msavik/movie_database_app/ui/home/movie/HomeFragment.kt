package com.msavik.movie_database_app.ui.home.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.msavik.domain.model.movie.Movie
import com.msavik.domain.utility.Resource
import com.msavik.movie_database_app.R
import com.msavik.movie_database_app.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MovieViewModel by sharedViewModel()
    val popularMovieAdapter = MovieAdapter()
    val topRatedMovieAdapter = MovieAdapter()
    val upcomingMovieAdapter = MovieAdapter()
    var popularMoviesList: List<Movie> = emptyList()
    var topRatedMoviesList: List<Movie> = emptyList()
    var upcomingMoviesList: List<Movie> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        viewModel.getPopularMoviesList()
        viewModel.getTopRatedMoviesList()
        viewModel.getUpcomingMoviesList()
        setMovieFilter()
    }

    private fun initObserver() {
        // viewModel.movieListLiveData.observe(viewLifecycleOwner)
        viewModel.popularMovieListLiveData.observe(this) { response ->
            when(response) {
                is Resource.Loading -> {
                    binding.srlHome.isRefreshing = true
                    Log.d(TAG, "Loading...")
                }
                is Resource.Success -> {
                    response.data?.let { movieList ->
                        binding.srlHome.isRefreshing = false
                        Log.d(TAG, "Success!")
                        popularMoviesList = movieList

                        Log.d(TAG, "LOGOBSERVER: $popularMoviesList")

                        initView()
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        binding.srlHome.isRefreshing = false
                        Log.e(TAG, "Error: $message")
                        Snackbar.make(requireView(), "Error: $message", Snackbar.LENGTH_LONG)
                    }
                }
            }
        }

        viewModel.topRatedMovieListLiveData.observe(this) { response ->
            when(response) {
                is Resource.Loading -> {
                    binding.srlHome.isRefreshing = true
                    Log.d(TAG, "Loading...")
                }
                is Resource.Success -> {
                    response.data?.let { movieList ->
                        binding.srlHome.isRefreshing = false
                        Log.d(TAG, "Success!")
                        topRatedMoviesList = movieList

                        Log.d(TAG, "LOGOBSERVER: $topRatedMoviesList")
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        binding.srlHome.isRefreshing = false
                        Log.e(TAG, "Error: $message")
                        Snackbar.make(requireView(), "Error: $message", Snackbar.LENGTH_LONG)
                    }
                }
            }
        }

        viewModel.upcomingMovieListLiveData.observe(this) { response ->
            when(response) {
                is Resource.Loading -> {
                    binding.srlHome.isRefreshing = true
                    Log.d(TAG, "Loading...")
                }
                is Resource.Success -> {
                    response.data?.let { movieList ->
                        binding.srlHome.isRefreshing = false
                        Log.d(TAG, "Success!")
                        upcomingMoviesList = movieList

                        Log.d(TAG, "LOGOBSERVER: $upcomingMoviesList")
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        binding.srlHome.isRefreshing = false
                        Log.e(TAG, "Error: $message")
                        Snackbar.make(requireView(), "Error: $message", Snackbar.LENGTH_LONG)
                    }
                }
            }
        }
    }

    private fun initView() {
        binding.apply {
            srlHome.setOnRefreshListener {
                when(tlHome.selectedTabPosition) {
                    0 -> { viewModel.getPopularMoviesList() }
                    1 -> { viewModel.getTopRatedMoviesList() }
                    2 -> { viewModel.getUpcomingMoviesList() }
                }
            }
        }

        initViewPager()
    }

    private fun initViewPager() {
        binding.apply {
            vpHome.adapter = HomePagerAdapter(this@HomeFragment)

            TabLayoutMediator(tlHome, vpHome) { tabLayout, position ->
                when(position) {
                    0 -> { tabLayout.text = "Popular" }
                    1 -> { tabLayout.text = "Top Rated" }
                    2 -> { tabLayout.text = "Upcoming" }
                }
            }.attach()
        }
    }

    private fun setMovieFilter() {
        binding.svHome.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var movieList: List<Movie> = emptyList()
                val movieAdapter: MovieAdapter
                val filteredMovies = ArrayList<Movie>()

                when(binding.tlHome.selectedTabPosition) {
                    0 -> {
                        movieList = popularMoviesList
                        movieAdapter = popularMovieAdapter
                    }
                    1 -> {
                        movieList = topRatedMoviesList
                        movieAdapter = topRatedMovieAdapter
                    }
                    2 -> {
                        movieList = upcomingMoviesList
                        movieAdapter = upcomingMovieAdapter
                    }
                    else -> {
                        movieAdapter = popularMovieAdapter
                    }
                }

                movieList.forEach {
                    if (it.title.lowercase().contains(newText?.lowercase() ?: "")) {
                        filteredMovies.add(it)
                    }
                }

                movieAdapter.submitList(filteredMovies)
                movieAdapter.notifyDataSetChanged()
                return false
            }
        })
    }

    companion object {
        val TAG: String = this::class.java.simpleName
    }
}