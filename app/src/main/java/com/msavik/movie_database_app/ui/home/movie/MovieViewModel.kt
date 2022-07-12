package com.msavik.movie_database_app.ui.home.movie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msavik.domain.model.movie.Movie
import com.msavik.domain.usecases.GetPopularMoviesListUseCase
import com.msavik.domain.utility.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(private val useCase: GetPopularMoviesListUseCase) : ViewModel() {

    val movieList: MutableLiveData<Resource<List<Movie>>> = MutableLiveData()

    fun getPopularMoviesList() = viewModelScope.launch(Dispatchers.IO) {
        movieList.postValue(Resource.Loading())

        try {
            val response = useCase.execute()
            movieList.postValue(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "getPopularMoviesList: $e")
            movieList.postValue(Resource.Error(e.message ?: e.toString()))
        }
    }

    companion object {
        val TAG = this::class.java.simpleName
    }
}