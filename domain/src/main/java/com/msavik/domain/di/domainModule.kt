package com.msavik.domain.di

import com.msavik.domain.usecases.GetPopularMoviesListUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { GetPopularMoviesListUseCase(get()) }
}