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
}