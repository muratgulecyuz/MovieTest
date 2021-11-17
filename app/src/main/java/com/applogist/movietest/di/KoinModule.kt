package com.applogist.movietest.di

import com.applogist.movietest.ui.detail.MovieDetailViewModel
import com.applogist.movietest.ui.search.MovieSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModules = module {
    single { NetworkModule().service() }
}
val viewModules = module {
    viewModel { MovieSearchViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }

}