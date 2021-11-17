package com.applogist.movietest.network

import com.applogist.movietest.network.response.MovieDetailResponse
import com.applogist.movietest.network.response.MovieItemResponse
import com.applogist.movietest.network.response.MovieResponse
import com.applogist.movietest.utils.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceInterface {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = API_KEY,
    ): MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int,
    ): MovieResponse


    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): MovieDetailResponse
}