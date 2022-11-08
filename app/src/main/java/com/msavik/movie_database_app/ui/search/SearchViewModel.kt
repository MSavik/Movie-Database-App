package com.msavik.movie_database_app.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msavik.domain.model.movie.Movie
import com.msavik.domain.usecases.SearchMoviesUseCase
import com.msavik.domain.utility.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    val searchMoviesListLiveData: MutableLiveData<Resource<List<Movie>>> = MutableLiveData()

    fun searchMovies(query: String) = viewModelScope.launch(Dispatchers.IO) {
        searchMoviesListLiveData.postValue(Resource.Loading())

        try {
            val response = searchMoviesUseCase.execute(query)
            searchMoviesListLiveData.postValue(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "searchMovies: $e")
            searchMoviesListLiveData.postValue(Resource.Error(e.message ?: e.toString()))
        }
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName
    }
}