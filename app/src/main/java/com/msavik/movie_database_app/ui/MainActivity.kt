package com.msavik.movie_database_app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.msavik.movie_database_app.databinding.ActivityMainBinding
import com.msavik.movie_database_app.ui.home.movie.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val movieViewModel: MovieViewModel by viewModel()

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}