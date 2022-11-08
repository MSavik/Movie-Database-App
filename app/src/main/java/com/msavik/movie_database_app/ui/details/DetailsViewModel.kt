package com.msavik.movie_database_app.ui.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msavik.domain.model.movie.Movie
import com.msavik.domain.usecases.*
import com.msavik.domain.utility.Page
import com.msavik.domain.utility.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getPopularMovieByIdUseCase: GetPopularMovieByIdUseCase,
    private val getTopRatedMovieByIdUseCase: GetTopRatedMovieByIdUseCase,
    private val getUpcomingMovieByIdUseCase: GetUpcomingMovieByIdUseCase,
    private val getSearchMovieByIdUseCase: GetSearchMovieByIdUseCase,
    private val getFavoriteMovieByIdUseCase: GetFavoriteMovieByIdUseCase,
    private val getFavoriteMoviesListUseCase: GetFavoriteMoviesListUseCase,
    private val saveFavoriteMovieUseCase: SaveFavoriteMovieUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase
) : ViewModel() {

    val movieLiveData: MutableLiveData<Resource<Movie>> = MutableLiveData()
    val favoriteMovieListLiveData: MutableLiveData<Resource<List<Movie>>> = MutableLiveData()

    fun getMovieById(id: Int, page: String) = viewModelScope.launch(Dispatchers.IO) {
        movieLiveData.postValue(Resource.Loading())

        try {
            val response = when(page) {
                Page.POPULAR.name -> {
                    getPopularMovieByIdUseCase.execute(id)
                }
                Page.TOP_RATED.name -> {
                    getTopRatedMovieByIdUseCase.execute(id)
                }
                Page.UPCOMING.name -> {
                    getUpcomingMovieByIdUseCase.execute(id)
                }
                Page.SEARCH.name -> {
                    getSearchMovieByIdUseCase.execute(id)
                }
                Page.FAVORITE.name -> {
                    getFavoriteMovieByIdUseCase.execute(id)
                }
                else -> {
                    throw Exception()
                }
            }

            movieLiveData.postValue(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "getMovieById: $e")
            movieLiveData.postValue(Resource.Error(e.message ?: e.toString()))
        }
    }

    fun getFavoriteMoviesList() = viewModelScope.launch(Dispatchers.IO) {
        favoriteMovieListLiveData.postValue(Resource.Loading())

        try {
            val response = getFavoriteMoviesListUseCase.execute()
            favoriteMovieListLiveData.postValue(Resource.Success(response))
        } catch (e: Exception) {
            favoriteMovieListLiveData.postValue(Resource.Error(e.message ?: e.toString()))
        }
    }

    fun saveFavoriteMovie(movie: Movie) = viewModelScope.launch(Dispatchers.IO) {

        try {
            saveFavoriteMovieUseCase.execute(movie)
        } catch (e: Exception) {
            Log.e(TAG, "saveFavoriteMovie: $e")
        }
    }

    fun deleteFavoriteMovie(movie: Movie) = viewModelScope.launch(Dispatchers.IO) {

        try {
            deleteFavoriteMovieUseCase.execute(movie)
        } catch (e: Exception) {
            Log.e(TAG, "deleteFavoriteMovie: $e")
        }
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName
    }
}