package com.msavik.domain.di

import com.msavik.domain.usecases.*
import org.koin.dsl.module

val domainModule = module {

    factory { GetPopularMoviesListUseCase(get()) }
    factory { GetTopRatedMoviesListUseCase(get()) }
    factory { GetUpcomingMoviesListUseCase(get()) }
    factory { GetPopularMovieByIdUseCase(get()) }
    factory { GetTopRatedMovieByIdUseCase(get()) }
    factory { GetUpcomingMovieByIdUseCase(get()) }
    factory { DeletePopularMovieListUseCase(get()) }
    factory { DeleteTopRatedMovieListUseCase(get()) }
    factory { DeleteUpcomingMovieListUseCase(get()) }
    factory { SearchMoviesUseCase(get()) }
    factory { GetSearchMovieByIdUseCase(get()) }
    factory { SaveFavoriteMovieUseCase(get()) }
    factory { GetFavoriteMovieByIdUseCase(get()) }
    factory { GetFavoriteMoviesListUseCase(get()) }
    factory { DeleteFavoriteMovieUseCase(get()) }
}