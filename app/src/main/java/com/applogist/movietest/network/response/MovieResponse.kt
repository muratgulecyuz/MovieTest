package com.applogist.movietest.network.response


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Response")
    val response: String?,
    @SerializedName("Search")
    val search: List<MovieItemResponse>?,
    @SerializedName("totalResults")
    val totalResults: String?
)

data class MovieItemResponse(
    @SerializedName("imdbID") val imdbID: String?,
    @SerializedName("Poster") val poster: String?,
    @SerializedName("Title") val title: String?,
    @SerializedName("Type") val type: String?,
    @SerializedName("Year") val year: String?
)