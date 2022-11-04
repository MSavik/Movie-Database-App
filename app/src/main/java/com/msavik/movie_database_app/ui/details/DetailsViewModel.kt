package com.msavik.movie_database_app.ui.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msavik.domain.model.movie.Movie
import com.msavik.domain.usecases.GetMovieByIdUseCase
import com.msavik.domain.utility.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getMovieByIdUseCase: GetMovieByIdUseCase
) : ViewModel() {

    val movieLiveData: MutableLiveData<Resource<Movie>> = MutableLiveData()

    fun getMovieById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        movieLiveData.postValue(Resource.Loading())

        try {
            val response = getMovieByIdUseCase.execute(id)
            movieLiveData.postValue(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "getMovieById: $e")
            movieLiveData.postValue(Resource.Error(e.message ?: e.toString()))
        }
    }

    companion object {
        val TAG: String = this::class.java.simpleName
    }
}