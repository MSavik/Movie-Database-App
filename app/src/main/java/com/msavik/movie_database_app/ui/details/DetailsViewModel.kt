package com.msavik.movie_database_app.ui.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msavik.domain.model.movie.Movie
import com.msavik.domain.usecases.GetPopularMovieByIdUseCase
import com.msavik.domain.usecases.GetSearchMovieByIdUseCase
import com.msavik.domain.usecases.GetTopRatedMovieByIdUseCase
import com.msavik.domain.usecases.GetUpcomingMovieByIdUseCase
import com.msavik.domain.utility.Page
import com.msavik.domain.utility.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getPopularMovieByIdUseCase: GetPopularMovieByIdUseCase,
    private val getTopRatedMovieByIdUseCase: GetTopRatedMovieByIdUseCase,
    private val getUpcomingMovieByIdUseCase: GetUpcomingMovieByIdUseCase,
    private val getSearchMovieByIdUseCase: GetSearchMovieByIdUseCase
) : ViewModel() {

    val movieLiveData: MutableLiveData<Resource<Movie>> = MutableLiveData()

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

    companion object {
        val TAG: String = this::class.java.simpleName
    }
}