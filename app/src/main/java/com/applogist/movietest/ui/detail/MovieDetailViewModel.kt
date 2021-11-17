package com.applogist.movietest.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.applogist.movietest.network.ServiceInterface
import com.applogist.movietest.network.response.MovieDetailResponse
import com.applogist.movietest.network.response.MovieItemResponse
import com.murgupluoglu.request.RESPONSE
import com.murgupluoglu.request.request

class MovieDetailViewModel(private val serviceInterface: ServiceInterface) : ViewModel() {
    val movieDetailResponse: MutableLiveData<RESPONSE<MovieDetailResponse>> = MutableLiveData()

    fun getMovieDetail(movieId: Int) {
        movieDetailResponse.request({ serviceInterface.getMovieDetail(movieId = movieId) })
    }
}