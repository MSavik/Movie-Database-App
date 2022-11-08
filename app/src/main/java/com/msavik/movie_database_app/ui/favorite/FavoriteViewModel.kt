package com.msavik.movie_database_app.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msavik.domain.model.movie.Movie
import com.msavik.domain.usecases.*
import com.msavik.domain.utility.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getFavoriteMoviesListUseCase: GetFavoriteMoviesListUseCase
) : ViewModel() {

    val favoriteMovieListLiveData: MutableLiveData<Resource<List<Movie>>> = MutableLiveData()

    fun getFavoriteMoviesList() = viewModelScope.launch(Dispatchers.IO) {
        favoriteMovieListLiveData.postValue(Resource.Loading())

        try {
            val response = getFavoriteMoviesListUseCase.execute()
            favoriteMovieListLiveData.postValue(Resource.Success(response))
        } catch (e: Exception) {
            favoriteMovieListLiveData.postValue(Resource.Error(e.message ?: e.toString()))
        }
    }
}