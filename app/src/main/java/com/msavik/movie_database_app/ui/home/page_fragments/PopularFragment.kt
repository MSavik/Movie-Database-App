package com.msavik.movie_database_app.ui.home.page_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msavik.movie_database_app.R
import com.msavik.movie_database_app.databinding.FragmentPopularBinding
import com.msavik.movie_database_app.ui.home.HomeFragment
import com.msavik.movie_database_app.ui.home.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PopularFragment : Fragment(R.layout.fragment_popular) {

    private lateinit var binding: FragmentPopularBinding
    private var homeFragment: HomeFragment? = null
    private val viewModel: MovieViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPopularBinding.bind(view)
        homeFragment = requireParentFragment() as HomeFragment

        homeFragment?.popularMoviesList = viewModel.popularMovieListLiveData.value?.data ?: emptyList()

        binding.rvPopular.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false)
            adapter = homeFragment?.popularMovieAdapter
        }
        homeFragment?.popularMovieAdapter?.submitList(homeFragment?.popularMoviesList)
        homeFragment?.popularMovieAdapter?.notifyDataSetChanged()
    }
}