package com.msavik.movie_database_app.di

import com.msavik.movie_database_app.ui.details.DetailsViewModel
import com.msavik.movie_database_app.ui.favorite.FavoriteViewModel
import com.msavik.movie_database_app.ui.home.MovieViewModel
import com.msavik.movie_database_app.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { MovieViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { DetailsViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { FavoriteViewModel(get(), get()) }
}