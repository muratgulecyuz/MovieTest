package com.applogist.movietest.network

import com.applogist.movietest.network.response.MovieDetailResponse
import com.applogist.movietest.network.response.MovieResponse
import com.applogist.movietest.utils.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceInterface {

    @GET("?apiKey=$API_KEY")
    suspend fun getMovies(
        @Query("s") searchKey: String
    ): MovieResponse

    @GET("?apiKey=$API_KEY")
    suspend fun getMovieDetail(
        @Query("i") imdbId: String
    ): MovieDetailResponse
}