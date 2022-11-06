package com.msavik.data.repository

import com.msavik.data.api.RetrofitInstance
import com.msavik.data.db.MovieDatabase
import com.msavik.data.entity.mapper.PopularMovieMapper
import com.msavik.data.entity.mapper.TopRatedMovieMapper
import com.msavik.data.entity.mapper.UpcomingMovieMapper
import com.msavik.data.utility.RetrofitUtils
import com.msavik.data.utility.RetrofitUtils.checkAndParseResponse
import com.msavik.domain.model.movie.Movie
import com.msavik.domain.repository.MovieRepository

class MovieRepositoryImpl(private val database: MovieDatabase) : MovieRepository {

    private val popularMovieMapper = PopularMovieMapper()
    private val topRatedMovieMapper = TopRatedMovieMapper()
    private val upcomingMovieMapper = UpcomingMovieMapper()

    /* API calls */

    override suspend fun getPopularMoviesList(): List<Movie> {
        val retrofitCall = RetrofitInstance.api.getPopularMoviesList()
        val response = RetrofitUtils.execute(retrofitCall)
        val movieList = response.checkAndParseResponse()
        return movieList.results
    }

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

    override suspend fun getMovieById(id: Int): Movie {
        val retrofitCall = RetrofitInstance.api.getMovieById(id)
        val response = RetrofitUtils.execute(retrofitCall)
        return response.checkAndParseResponse()
    }


    /* DB calls */

    /* Popular */
    override suspend fun upsertPopularMovieDatabase(movie: Movie) =
        database.getMovieDao().upsertPopularMovie(popularMovieMapper.mapToEntity(movie))

    override fun getPopularMovieByIdDatabase(id: Int): Movie {
        val movieEntity = database.getMovieDao().getPopularMovieById(id)
        return popularMovieMapper.mapFromEntity(movieEntity)
    }

    override fun getPopularMovieListDatabase(): List<Movie> {
        val movieEntityList = database.getMovieDao().getAllPopularMovies()
        val movieList = arrayListOf<Movie>()

        movieEntityList.forEach { movieEntity ->
            movieList.add(popularMovieMapper.mapFromEntity(movieEntity))
        }
        return movieList
    }

    override suspend fun deletePopularMovieDatabase(movie: Movie) =
        database.getMovieDao().deletePopularMovie(popularMovieMapper.mapToEntity(movie))

    override suspend fun deleteAllPopularMoviesDatabase() {
        database.getMovieDao().deleteAllPopularMovies()
    }

    /* TopRated */
    override suspend fun upsertTopRatedMovieDatabase(movie: Movie) =
        database.getMovieDao().upsertTopRatedMovie(topRatedMovieMapper.mapToEntity(movie))

    override fun getTopRatedMovieByIdDatabase(id: Int): Movie {
        val movieEntity = database.getMovieDao().getTopRatedMovieById(id)
        return topRatedMovieMapper.mapFromEntity(movieEntity)
    }

    override fun getTopRatedMovieListDatabase(): List<Movie> {
        val movieEntityList = database.getMovieDao().getAllTopRatedMovies()
        val movieList = arrayListOf<Movie>()

        movieEntityList.forEach { movieEntity ->
            movieList.add(topRatedMovieMapper.mapFromEntity(movieEntity))
        }
        return movieList
    }

    override suspend fun deleteTopRatedMovieDatabase(movie: Movie) =
        database.getMovieDao().deleteTopRatedMovie(topRatedMovieMapper.mapToEntity(movie))

    override suspend fun deleteAllTopRatedMoviesDatabase() {
        database.getMovieDao().deleteAllTopRatedMovies()
    }

    /* Upcoming */
    override suspend fun upsertUpcomingMovieDatabase(movie: Movie) =
        database.getMovieDao().upsertUpcomingMovie(upcomingMovieMapper.mapToEntity(movie))

    override fun getUpcomingMovieByIdDatabase(id: Int): Movie {
        val movieEntity = database.getMovieDao().getUpcomingMovieById(id)
        return upcomingMovieMapper.mapFromEntity(movieEntity)
    }

    override fun getUpcomingMovieListDatabase(): List<Movie> {
        val movieEntityList = database.getMovieDao().getAllUpcomingMovies()
        val movieList = arrayListOf<Movie>()

        movieEntityList.forEach { movieEntity ->
            movieList.add(upcomingMovieMapper.mapFromEntity(movieEntity))
        }
        return movieList
    }

    override suspend fun deleteUpcomingMovieDatabase(movie: Movie) =
        database.getMovieDao().deleteUpcomingMovie(upcomingMovieMapper.mapToEntity(movie))

    override suspend fun deleteAllUpcomingMoviesDatabase() {
        database.getMovieDao().deleteAllUpcomingMovies()
    }
}