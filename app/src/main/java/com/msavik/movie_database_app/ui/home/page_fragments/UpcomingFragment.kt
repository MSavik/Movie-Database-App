package com.msavik.movie_database_app.ui.home.page_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msavik.movie_database_app.R
import com.msavik.movie_database_app.databinding.FragmentUpcomingBinding
import com.msavik.movie_database_app.ui.home.HomeFragment
import com.msavik.movie_database_app.ui.home.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UpcomingFragment : Fragment(R.layout.fragment_upcoming) {

    private lateinit var binding: FragmentUpcomingBinding
    private var homeFragment: HomeFragment? = null
    private val viewModel: MovieViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUpcomingBinding.bind(view)
        homeFragment = requireParentFragment() as HomeFragment

        homeFragment?.upcomingMoviesList = viewModel.upcomingMovieListLiveData.value?.data ?: emptyList()

        binding.rvUpcoming.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false)
            adapter = homeFragment?.upcomingMovieAdapter
        }
        homeFragment?.upcomingMovieAdapter?.submitList(homeFragment?.upcomingMoviesList)
        homeFragment?.upcomingMovieAdapter?.notifyDataSetChanged()
    }
}