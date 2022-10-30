package com.msavik.data.repository

import com.msavik.data.api.RetrofitInstance
import com.msavik.data.utility.RetrofitUtils
import com.msavik.data.utility.RetrofitUtils.checkAndParseResponse
import com.msavik.domain.model.movie.Movie
import com.msavik.domain.repository.MovieRepository

class MovieRepositoryImpl : MovieRepository {

    override suspend fun getPopularMoviesList(): List<Movie> {
        val retrofitCall = RetrofitInstance.api.getPopularMoviesList()
        val response = RetrofitUtils.execute(retrofitCall)
        val movieList = response.checkAndParseResponse()
        return movieList.results

//        if (response.isSuccessful && response.body() != null) {
//            return response.body()!!.results
//        } else {
//            throw Exception(response.errorBody().toString())
//        }
    }

//    override suspend fun getPopularMoviesList(): List<Movie> {
//        val retrofitCall = RetrofitInstance.api.getPopularMoviesList()
//        try {
//            val response = retrofitCall.execute()
//            if (response.isSuccessful && response.body() != null) {
//                return response.body()!!.results
//            } else {
//                throw Exception(response.errorBody().toString())
//            }
//        } catch (e: Exception) {
//            throw Exception("NETWORK ERROR")
//        }
//    }

    override suspend fun getTopRatedMoviesList(): List<Movie> {
        val retrofitCall = RetrofitInstance.api.getTopRatedMoviesList()
        val response = RetrofitUtils.execute(retrofitCall)
        val movieList = response.checkAndParseResponse()
        return movieList.results
    }

    override suspend fun getUpcomingMoviesList(): List<Movie> {
        val retrofitCall = RetrofitInstance.api.getUpcomingMoviesList()
        val response = RetrofitUtils.execute(retrofitCall)
        val movieList = response.checkAndParseResponse()
        return movieList.results
    }
}