package com.applogist.movietest.di

import androidx.lifecycle.SavedStateHandle
import com.applogist.movietest.ui.detail.MovieDetailViewModel
import com.applogist.movietest.ui.movies.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModules = module {
    single { NetworkModule().service() }
}
val viewModules = module {
    viewModel { MoviesViewModel(get(), SavedStateHandle()) }
    viewModel { MovieDetailViewModel(get()) }

}