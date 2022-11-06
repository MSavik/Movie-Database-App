package com.msavik.data.di

import com.msavik.data.db.MovieDatabase
import com.msavik.data.repository.MovieRepositoryImpl
import com.msavik.domain.repository.MovieRepository
import org.koin.dsl.module

val dataModule = module {

    single { MovieDatabase(get()) }

    single<MovieRepository> { MovieRepositoryImpl(get()) }
}