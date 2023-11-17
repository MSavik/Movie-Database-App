package com.msavik.movie_database_app.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msavik.domain.model.movie.Movie
import com.msavik.domain.usecases.*
import com.msavik.domain.utility.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(
    private val getPopularMoviesListUseCase: GetPopularMoviesListUseCase,
    private val getTopRatedMoviesListUseCase: GetTopRatedMoviesListUseCase,
    private val getUpcomingMoviesListUseCase: GetUpcomingMoviesListUseCase,
    private val deletePopularMovieListUseCase: DeletePopularMovieListUseCase,
    private val deleteTopRatedMovieListUseCase: DeleteTopRatedMovieListUseCase,
    private val deleteUpcomingMovieListUseCase: DeleteUpcomingMovieListUseCase,
    private val updatePopularMoviesListUseCase: UpdatePopularMoviesListUseCase,
    private val updateTopRatedMoviesListUseCase: UpdateTopRatedMoviesListUseCase,
    private val updateUpcomingMoviesListUseCase: UpdateUpcomingMoviesListUseCase
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

    fun deletePopularMovieList() = viewModelScope.launch(Dispatchers.IO) {
        try {
            deletePopularMovieListUseCase.execute()
            val response = getPopularMoviesListUseCase.execute()
            popularMovieListLiveData.postValue(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "deletePopularMovieList: $e")
            popularMovieListLiveData.postValue(Resource.Error(e.message ?: e.toString()))
        }
    }

    fun deleteTopRatedMovieList() = viewModelScope.launch(Dispatchers.IO) {
        try {
            deleteTopRatedMovieListUseCase.execute()
            val response = getTopRatedMoviesListUseCase.execute()
            topRatedMovieListLiveData.postValue(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "deleteTopRatedMovieList: $e")
            topRatedMovieListLiveData.postValue(Resource.Error(e.message ?: e.toString()))
        }
    }

    fun deleteUpcomingMovieList() = viewModelScope.launch(Dispatchers.IO) {
        try {
            deleteUpcomingMovieListUseCase.execute()
            val response = getUpcomingMoviesListUseCase.execute()
            upcomingMovieListLiveData.postValue(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "deleteUpcomingMovieList: $e")
            upcomingMovieListLiveData.postValue(Resource.Error(e.message ?: e.toString()))
        }
    }

    fun updatePopularMoviesList() = viewModelScope.launch(Dispatchers.IO) {
        try {
            updatePopularMoviesListUseCase.execute()
            val response = getPopularMoviesListUseCase.execute()
            popularMovieListLiveData.postValue(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "updatePopularMoviesList")
            popularMovieListLiveData.postValue(Resource.Error(e.message ?: e.toString()))
        }
    }

    fun updateTopRatedMoviesList() = viewModelScope.launch(Dispatchers.IO) {
        try {
            updateTopRatedMoviesListUseCase.execute()
            val response = getTopRatedMoviesListUseCase.execute()
            topRatedMovieListLiveData.postValue(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "updateTopRatedMoviesList")
            topRatedMovieListLiveData.postValue(Resource.Error(e.message ?: e.toString()))
        }
    }

    fun updateUpcomingMoviesList() = viewModelScope.launch(Dispatchers.IO) {
        try {
            updateUpcomingMoviesListUseCase.execute()
            val response = getUpcomingMoviesListUseCase.execute()
            upcomingMovieListLiveData.postValue(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "updateUpcomingMoviesList")
            upcomingMovieListLiveData.postValue(Resource.Error(e.message ?: e.toString()))
        }
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName
    }
}