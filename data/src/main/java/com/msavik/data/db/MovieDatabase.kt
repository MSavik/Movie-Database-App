package com.msavik.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.msavik.data.entity.PopularMovieEntity
import com.msavik.data.entity.TopRatedMovieEntity
import com.msavik.data.entity.UpcomingMovieEntity

@Database(entities = [PopularMovieEntity::class, TopRatedMovieEntity::class, UpcomingMovieEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: createDatabase(context).also { INSTANCE = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java,
                "movie_database.db"
            )
                .allowMainThreadQueries()
                .build()
    }
}