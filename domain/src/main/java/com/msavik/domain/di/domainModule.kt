package com.msavik.domain.di

import com.msavik.domain.usecases.GetPopularMoviesListUseCase
import com.msavik.domain.usecases.GetTopRatedMoviesListUseCase
import com.msavik.domain.usecases.GetUpcomingMoviesListUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { GetPopularMoviesListUseCase(get()) }
    factory { GetTopRatedMoviesListUseCase(get()) }
    factory { GetUpcomingMoviesListUseCase(get()) }
}