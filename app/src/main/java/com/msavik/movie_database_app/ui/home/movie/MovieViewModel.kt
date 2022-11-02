package com.msavik.movie_database_app.ui.home.movie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msavik.domain.model.movie.Movie
import com.msavik.domain.usecases.GetPopularMoviesListUseCase
import com.msavik.domain.usecases.GetTopRatedMoviesListUseCase
import com.msavik.domain.usecases.GetUpcomingMoviesListUseCase
import com.msavik.domain.utility.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(
    private val getPopularMoviesListUseCase: GetPopularMoviesListUseCase,
    private val getTopRatedMoviesListUseCase: GetTopRatedMoviesListUseCase,
    private val getUpcomingMoviesListUseCase: GetUpcomingMoviesListUseCase,
) : ViewModel() {

    val popularMovieListLiveData: MutableLiveData<Resource<List<Movie>>> = MutableLiveData()
    val topRatedMovieListLiveData: MutableLiveData<Resource<List<Movie>>> = MutableLiveData()
    val upcomingMovieListLiveData: MutableLiveData<Resource<List<Movie>>> = MutableLiveData()

    fun getPopularMoviesList() = viewModelScope.launch(Dispatchers.IO) {
        popularMovieListLiveData.postValue(Resource.Loading())

        try {
            val response = getPopularMoviesListUseCase.execute()
            popularMovieListLiveData.postValue(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "getPopularMoviesList: $e")
            popularMovieListLiveData.postValue(Resource.Error(e.message ?: e.toString()))
        }
    }

    fun getTopRatedMoviesList() = viewModelScope.launch(Dispatchers.IO) {
        topRatedMovieListLiveData.postValue(Resource.Loading())

        try {
            val response = getTopRatedMoviesListUseCase.execute()
            topRatedMovieListLiveData.postValue(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "getTopRatedMoviesList: $e")
            topRatedMovieListLiveData.postValue(Resource.Error(e.message ?: e.toString()))
        }
    }

    fun getUpcomingMoviesList() = viewModelScope.launch(Dispatchers.IO) {
        upcomingMovieListLiveData.postValue(Resource.Loading())

        try {
            val response = getUpcomingMoviesListUseCase.execute()
            upcomingMovieListLiveData.postValue(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "getUpcomingMoviesList: $e")
            upcomingMovieListLiveData.postValue(Resource.Error(e.message ?: e.toString()))
        }
    }

    companion object {
        val TAG: String = this::class.java.simpleName
    }
}