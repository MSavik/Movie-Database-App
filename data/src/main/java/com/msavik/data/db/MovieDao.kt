package com.msavik.data.db

import androidx.room.*
import com.msavik.data.entity.FavoriteMovieEntity
import com.msavik.data.entity.PopularMovieEntity
import com.msavik.data.entity.TopRatedMovieEntity
import com.msavik.data.entity.UpcomingMovieEntity

@Dao
interface MovieDao {

    /* Popular */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPopularMovie(movie: PopularMovieEntity)

    @Query("SELECT * FROM popular_movie WHERE id LIKE :id")
    fun getPopularMovieById(id: Int): PopularMovieEntity

    @Query("SELECT * FROM popular_movie ORDER BY id")
    fun getAllPopularMovies(): List<PopularMovieEntity>

    @Delete
    suspend fun deletePopularMovie(movie: PopularMovieEntity)

    @Query("DELETE FROM popular_movie")
    suspend fun deleteAllPopularMovies()

    /* TopRated */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTopRatedMovie(movie: TopRatedMovieEntity)

    @Query("SELECT * FROM top_rated_movie WHERE id LIKE :id")
    fun getTopRatedMovieById(id: Int): TopRatedMovieEntity

    @Query("SELECT * FROM top_rated_movie ORDER BY id")
    fun getAllTopRatedMovies(): List<TopRatedMovieEntity>

    @Delete
    suspend fun deleteTopRatedMovie(movie: TopRatedMovieEntity)

    @Query("DELETE FROM top_rated_movie")
    suspend fun deleteAllTopRatedMovies()


    /* Upcoming */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUpcomingMovie(movie: UpcomingMovieEntity)

    @Query("SELECT * FROM upcoming_movie WHERE id LIKE :id")
    fun getUpcomingMovieById(id: Int): UpcomingMovieEntity

    @Query("SELECT * FROM upcoming_movie ORDER BY id")
    fun getAllUpcomingMovies(): List<UpcomingMovieEntity>

    @Delete
    suspend fun deleteUpcomingMovie(movie: UpcomingMovieEntity)

    @Query("DELETE FROM upcoming_movie")
    suspend fun deleteAllUpcomingMovies()

    /* Favorite */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertFavoriteMovie(movie: FavoriteMovieEntity)

    @Query("SELECT * FROM favorite_movie WHERE id LIKE :id")
    fun getFavoriteMovieById(id: Int): FavoriteMovieEntity

    @Query("SELECT * FROM favorite_movie ORDER BY id")
    fun getAllFavoriteMovies(): List<FavoriteMovieEntity>

    @Delete
    suspend fun deleteFavoriteMovie(movie: FavoriteMovieEntity)
}