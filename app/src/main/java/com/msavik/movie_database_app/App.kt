package com.msavik.movie_database_app

import android.app.Application
import com.msavik.data.di.dataModule
import com.msavik.domain.di.domainModule
import com.msavik.movie_database_app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule, domainModule, dataModule)
        }
    }
}