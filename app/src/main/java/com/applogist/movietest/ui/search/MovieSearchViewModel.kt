package com.applogist.movietest.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.applogist.movietest.network.ServiceInterface
import com.applogist.movietest.network.response.MovieItemResponse
import com.applogist.movietest.network.response.MovieResponse
import com.murgupluoglu.request.RESPONSE
import com.murgupluoglu.request.request

class MovieSearchViewModel(private val serviceInterface: ServiceInterface) : ViewModel() {
    val movieList = arrayListOf<MovieItemResponse>()

    val moviesResponse: MutableLiveData<RESPONSE<MovieResponse>> = MutableLiveData()
    fun getMovies(searchKey: String = "") {
        moviesResponse.request({ serviceInterface.getMovies(searchKey) })
    }

    fun addMovies(receivedMovieList: List<MovieItemResponse>?) {
        receivedMovieList?.let {
            movieList.clear()
            movieList.addAll(it)
        }

    }
}