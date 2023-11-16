package com.msavik.movie_database_app.ui.home.page_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msavik.movie_database_app.R
import com.msavik.movie_database_app.databinding.FragmentTopRatedBinding
import com.msavik.movie_database_app.ui.home.HomeFragment
import com.msavik.movie_database_app.ui.home.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TopRatedFragment : Fragment(R.layout.fragment_top_rated) {

    private lateinit var binding: FragmentTopRatedBinding
    private  var homeFragment: HomeFragment? = null
    private val viewModel: MovieViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTopRatedBinding.bind(view)
        homeFragment = requireParentFragment() as HomeFragment

        homeFragment?.topRatedMoviesList = viewModel.topRatedMovieListLiveData.value?.data ?: emptyList()

        binding.rvTopRated.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false)
            adapter = homeFragment?.topRatedMovieAdapter
        }
        homeFragment?.topRatedMovieAdapter?.submitList(homeFragment?.topRatedMoviesList)
        homeFragment?.topRatedMovieAdapter?.notifyDataSetChanged()
    }
}