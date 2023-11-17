package com.msavik.movie_database_app.ui.favorite

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msavik.domain.model.movie.Movie
import com.msavik.domain.usecases.*
import com.msavik.domain.utility.Resource
import com.msavik.movie_database_app.ui.details.DetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getFavoriteMoviesListUseCase: GetFavoriteMoviesListUseCase,
    private val deleteFavoriteMovieListUseCase: DeleteFavoriteMovieListUseCase
) : ViewModel() {

    val favoriteMovieListLiveData: MutableLiveData<Resource<List<Movie>>> = MutableLiveData()

    fun getFavoriteMoviesList() = viewModelScope.launch(Dispatchers.IO) {
        favoriteMovieListLiveData.postValue(Resource.Loading())

        try {
            val response = getFavoriteMoviesListUseCase.execute()
            favoriteMovieListLiveData.postValue(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(DetailsViewModel.TAG, "getFavoriteMoviesList: $e")
            favoriteMovieListLiveData.postValue(Resource.Error(e.message ?: e.toString()))
        }
    }

    fun deleteFavoriteMovieList() = viewModelScope.launch(Dispatchers.IO) {
        try {
            deleteFavoriteMovieListUseCase.execute()
            val response = getFavoriteMoviesListUseCase.execute()
            favoriteMovieListLiveData.postValue(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(DetailsViewModel.TAG, "deleteFavoriteMovieList: $e")
            favoriteMovieListLiveData.postValue(Resource.Error(e.message ?: e.toString()))
        }
    }
}