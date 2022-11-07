package com.msavik.movie_database_app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.msavik.movie_database_app.R
import com.msavik.movie_database_app.databinding.ActivityMainBinding
import com.msavik.movie_database_app.ui.home.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val movieViewModel: MovieViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isFirstStart) {
            isFirstStart = false
            clearDatabase()
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment) as NavHostFragment

        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.bnvMain, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.detailsFragment) {
                binding.bnvMain.visibility = View.GONE
            } else {
                binding.bnvMain.visibility = View.VISIBLE
            }
        }
    }

    private fun clearDatabase() {
        movieViewModel.deletePopularMovieList()
        movieViewModel.deleteTopRatedMovieList()
        movieViewModel.deleteUpcomingMovieList()
    }

    companion object {
        private var isFirstStart = true
    }
}