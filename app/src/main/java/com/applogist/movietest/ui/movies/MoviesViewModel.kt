package com.applogist.movietest.ui.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.applogist.movietest.network.ServiceInterface
import com.applogist.movietest.network.response.MovieItemResponse
import com.applogist.movietest.network.response.MovieResponse
import com.applogist.movietest.utils.PAGING_PARAMS
import com.murgupluoglu.request.RESPONSE
import com.murgupluoglu.request.request
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class MoviesViewModel(
    private val serviceInterface: ServiceInterface,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val nowPlayingMovieList = arrayListOf<MovieItemResponse>()

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)


    val moviesResponse: MutableLiveData<RESPONSE<MovieResponse>> = MutableLiveData()
    fun getMovies() {
        moviesResponse.request({ serviceInterface.getNowPlayingMovies() })
    }

    fun addMovies(receivedMovieList: List<MovieItemResponse>?) {
        receivedMovieList?.let {
            nowPlayingMovieList.clear()
            nowPlayingMovieList.addAll(it.take(5))
        }

    }

    fun getUpComingMovies(): Flow<PagingData<MovieItemResponse>> {
        @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
        return flowOf(
            clearListCh.receiveAsFlow().map { PagingData.empty() },
            savedStateHandle.getLiveData<String>(PAGING_PARAMS).asFlow()
                .flatMapLatest {
                    Pager(
                        PagingConfig(20)
                    ) {
                        UpComingMoviesPagingSource(
                            serviceInterface
                        )
                    }.flow
                }
        ).flattenMerge(2)

    }
}