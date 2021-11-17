package com.applogist.movietest

import android.app.Application
import com.applogist.movietest.di.koinModules
import com.applogist.movietest.di.viewModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(arrayListOf(koinModules, viewModules))
        }
    }
}