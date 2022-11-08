package com.msavik.data.entity.mapper

import com.msavik.data.entity.FavoriteMovieEntity
import com.msavik.domain.model.movie.Movie

class FavoriteMovieMapper: Mapper<FavoriteMovieEntity, Movie> {
    override fun mapFromEntity(type: FavoriteMovieEntity): Movie {
        return Movie(
            type.adult, type.backdrop_path, type.belongs_to_collection, type.budget, type.genres, type.homepage,
            type.id, type.imdb_id, type.original_language, type.original_title, type.overview, type.popularity,
            type.poster_path, type.production_companies, type.production_countries, type.release_date, type.revenue,
            type.runtime, type.spoken_languages, type.status, type.tagline, type.title, type.video, type.vote_average, type.vote_count
        )
    }

    override fun mapToEntity(type: Movie): FavoriteMovieEntity {
        return FavoriteMovieEntity(
            type.adult, type.backdrop_path, type.belongs_to_collection, type.budget, type.genres, type.homepage,
            type.id, type.imdb_id, type.original_language, type.original_title, type.overview, type.popularity,
            type.poster_path, type.production_companies, type.production_countries, type.release_date, type.revenue,
            type.runtime, type.spoken_languages, type.status, type.tagline, type.title, type.video, type.vote_average, type.vote_count
        )
    }
}