package com.msavik.movie_database_app.ui.favorite

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.Window
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
import com.msavik.movie_database_app.databinding.DialogClearFavoriteMoviesBinding
import com.msavik.movie_database_app.databinding.FragmentFavoriteBinding
import com.msavik.movie_database_app.ui.home.MovieAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var dialogBinding: DialogClearFavoriteMoviesBinding
    private val viewModel: FavoriteViewModel by sharedViewModel()
    private val favoriteMovieAdapter = MovieAdapter { movieId ->
        onItemClick(movieId)
    }
    private var favoriteMovieList: List<Movie> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)

        initObserver()
        binding.srlFavorite.setOnRefreshListener {
            viewModel.getFavoriteMoviesList()
        }
        setMovieFilter()
        viewModel.getFavoriteMoviesList()
    }

    private fun initObserver() {
        viewModel.favoriteMovieListLiveData.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Loading -> {
                    binding.srlFavorite.isRefreshing = true
                    Log.d(TAG, "Loading...")
                }
                is Resource.Success -> {
                    response.data?.let { movieList ->
                        binding.srlFavorite.isRefreshing = false
                        Log.d(TAG, "Success!")
                        favoriteMovieList = movieList

                        Log.d(TAG, "LOGOBSERVER: $favoriteMovieList")

                        initView()
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        binding.srlFavorite.isRefreshing = false
                        Log.e(TAG, "Error: $message")
                        Snackbar.make(requireView(), "Error: $message", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun initView() {
        binding.btClear.setOnClickListener {
            dialogBinding = DialogClearFavoriteMoviesBinding.inflate(layoutInflater)
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(dialogBinding.root)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialogBinding.btNo.setOnClickListener {
                dialog.dismiss()
            }

            dialogBinding.btYes.setOnClickListener {
                viewModel.deleteAllFavoriteMovies()
                dialog.dismiss()
            }

            dialog.show()
        }

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false)
            adapter = favoriteMovieAdapter
        }
        favoriteMovieAdapter.submitList(favoriteMovieList)
        favoriteMovieAdapter.notifyDataSetChanged()
    }

    private fun setMovieFilter() {
        binding.svFavorite.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredMovies = ArrayList<Movie>()

                favoriteMovieList.forEach {
                    if (it.title?.lowercase()?.contains(newText?.lowercase() ?: "") == true) {
                        filteredMovies.add(it)
                    }
                }
                favoriteMovieAdapter.submitList(filteredMovies)
                favoriteMovieAdapter.notifyDataSetChanged()
                return false
            }
        })
    }

    private fun onItemClick(movieId: Int) {
        val bundle = bundleOf(
            "movieId" to movieId.toString(),
            "page" to Page.FAVORITE.name
        )
        findNavController().navigate(R.id.action_favoriteFragment_to_detailsFragment, bundle)
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName
    }
}