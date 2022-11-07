package com.msavik.data.api

import com.msavik.data.utility.BaseUrl.TMDB_API_KEY
import com.msavik.domain.model.movie.Movie
import com.msavik.domain.model.movie.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {

    @GET("movie/{id}")
    fun getMovieById(
        @Path("id")
        id: Int,
        @Query("api_key")
        api_key: String = TMDB_API_KEY
    ): Call<Movie>

    @GET("find/{id}")
    fun findMovieById(
        @Path("imdb_id")
        imdb_id: Int,
        @Query("api_key")
        api_key: String = TMDB_API_KEY,
        @Query("external_source")
        external_source: String = "imdb_id"
    ): Call<Movie>

    @GET("movie/popular")
    fun getPopularMoviesList(
        @Query("api_key")
        api_key: String = TMDB_API_KEY,
        @Query("page")
        page: Int = 1
    ): Call<MovieList>

    @GET("movie/top_rated")
    fun getTopRatedMoviesList(
        @Query("api_key")
        api_key: String = TMDB_API_KEY,
        @Query("page")
        page: Int = 1
    ): Call<MovieList>

    @GET("movie/upcoming")
    fun getUpcomingMoviesList(
        @Query("api_key")
        api_key: String = TMDB_API_KEY,
        @Query("page")
        page: Int = 1
    ): Call<MovieList>

    @GET("search/movie")
    fun searchMovies(
        @Query("query")
        query: String,
        @Query("api_key")
        api_key: String = TMDB_API_KEY,
        @Query("page")
        page: Int = 1
    ): Call<MovieList>
}