package com.msavik.movie_database_app.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.msavik.domain.model.movie.Movie
import com.msavik.domain.utility.Page
import com.msavik.domain.utility.Resource
import com.msavik.movie_database_app.R
import com.msavik.movie_database_app.databinding.FragmentHomeBinding
import com.msavik.movie_database_app.ui.home.page_fragments.PopularFragment
import com.msavik.movie_database_app.ui.home.page_fragments.TopRatedFragment
import com.msavik.movie_database_app.ui.home.page_fragments.UpcomingFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MovieViewModel by sharedViewModel()

    val popularMovieAdapter = MovieAdapter { movieId ->
        onItemClick(movieId)
    }
    val topRatedMovieAdapter = MovieAdapter { movieId ->
        onItemClick(movieId)
    }
    val upcomingMovieAdapter = MovieAdapter { movieId ->
        onItemClick(movieId)
    }

    var popularMoviesList: List<Movie> = emptyList()
    var topRatedMoviesList: List<Movie> = emptyList()
    var upcomingMoviesList: List<Movie> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        binding = FragmentHomeBinding.bind(view)

        viewModel.getPopularMoviesList()
        viewModel.getTopRatedMoviesList()
        viewModel.getUpcomingMoviesList()
        setMovieFilter()
    }

    private fun initObserver() {
        viewModel.popularMovieListLiveData.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Loading -> {
                    binding.srlHome.isRefreshing = true
                    Log.d(TAG, "Loading popular movies...")
                }
                is Resource.Success -> {
                    response.data?.let { movieList ->
                        binding.srlHome.isRefreshing = false
                        Log.d(TAG, "Success! Popular movies loaded")
                        popularMoviesList = movieList

                        Log.d(TAG, "LOGOBSERVER: $popularMoviesList")

                        initView()
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        binding.srlHome.isRefreshing = false
                        Log.e(TAG, "Error loading popular movies: $message")
                        Snackbar.make(requireView(), "Error: $message", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }

        viewModel.topRatedMovieListLiveData.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Loading -> {
                    binding.srlHome.isRefreshing = true
                    Log.d(TAG, "Loading top rated movies...")
                }
                is Resource.Success -> {
                    response.data?.let { movieList ->
                        binding.srlHome.isRefreshing = false
                        Log.d(TAG, "Success! Top rated movies loaded")
                        topRatedMoviesList = movieList

                        Log.d(TAG, "LOGOBSERVER: $topRatedMoviesList")

                        initView()
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        binding.srlHome.isRefreshing = false
                        Log.e(TAG, "Error loading top rated movies: $message")
                        Snackbar.make(requireView(), "Error: $message", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }

        viewModel.upcomingMovieListLiveData.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Loading -> {
                    binding.srlHome.isRefreshing = true
                    Log.d(TAG, "Loading upcoming movies...")
                }
                is Resource.Success -> {
                    response.data?.let { movieList ->
                        binding.srlHome.isRefreshing = false
                        Log.d(TAG, "Success! Upcoming movies loaded")
                        upcomingMoviesList = movieList

                        Log.d(TAG, "LOGOBSERVER: $upcomingMoviesList")

                        initView()
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        binding.srlHome.isRefreshing = false
                        Log.e(TAG, "Error loading upcoming movies: $message")
                        Snackbar.make(requireView(), "Error: $message", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun initView() {
        binding.apply {
            srlHome.setOnRefreshListener {
                when(tlHome.selectedTabPosition) {
                    0 -> { viewModel.updatePopularMoviesList() }
                    1 -> { viewModel.updateTopRatedMoviesList() }
                    2 -> { viewModel.updateUpcomingMoviesList() }
                }
            }
        }

        initViewPager()
    }

    private fun initViewPager() {
        binding.apply {
            val fragmentFactory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return when (className) {
                        PopularFragment::class.java.name -> PopularFragment()
                        TopRatedFragment::class.java.name -> TopRatedFragment()
                        UpcomingFragment::class.java.name -> UpcomingFragment()
                        else -> super.instantiate(classLoader, className)
                    }
                }
            }
            vpHome.adapter = HomePagerAdapter(this@HomeFragment, fragmentFactory)

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
                val movieList: List<Movie>
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
                        movieList = popularMoviesList
                        movieAdapter = popularMovieAdapter
                    }
                }

                movieList.forEach {
                    if (it.title?.lowercase()?.contains(newText?.lowercase() ?: "") == true) {
                        filteredMovies.add(it)
                    }
                }

                movieAdapter.submitList(filteredMovies)
                movieAdapter.notifyDataSetChanged()
                return false
            }
        })
    }

    private fun onItemClick(movieId: Int) {

        val page = when(binding.tlHome.selectedTabPosition) {
            0 -> Page.POPULAR.name
            1 -> Page.TOP_RATED.name
            2 -> Page.UPCOMING.name
            else -> Page.POPULAR.name
        }

        val bundle = bundleOf(
            "movieId" to movieId.toString(),
            "page" to page
        )
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName
    }
}