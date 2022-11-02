package com.msavik.movie_database_app.di

import com.msavik.movie_database_app.ui.details.DetailsViewModel
import com.msavik.movie_database_app.ui.home.movie.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { MovieViewModel(get(), get(), get()) }
    viewModel { DetailsViewModel(get()) }
}