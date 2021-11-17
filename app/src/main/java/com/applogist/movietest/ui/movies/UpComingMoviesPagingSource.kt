package com.applogist.movietest.ui.movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.applogist.movietest.di.NetworkModule
import com.applogist.movietest.network.ServiceInterface
import com.applogist.movietest.network.response.MovieItemResponse

class UpComingMoviesPagingSource(private val serviceInterface: ServiceInterface) :
    PagingSource<String, MovieItemResponse>() {
    override fun getRefreshKey(state: PagingState<String, MovieItemResponse>): String? {
        return ""
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, MovieItemResponse> {
        var pageIndex = 1

        if (!params.key.isNullOrEmpty()) {
            pageIndex = params.key!!.toInt()
        }
        val response =
            serviceInterface
                .getUpComingMovies(page = pageIndex).results
        return if ((response.isNotEmpty())) {
            pageIndex++

            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = pageIndex.toString()
            )
        } else {
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = null
            )
        }
    }
}