package com.msavik.domain.model.movie

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Movie(

    @SerializedName("adult") @Expose
    val adult: Boolean?,

    @SerializedName("backdrop_path") @Expose
    val backdrop_path: String?,

    @SerializedName("belongs_to_collection") @Expose
    val belongs_to_collection: MovieCollection?,

    @SerializedName("budget") @Expose
    val budget: Int?,

    @SerializedName("genres") @Expose
    val genres: List<MovieGenre>?,

    @SerializedName("homepage") @Expose
    val homepage: String?,

    @SerializedName("id") @Expose
    val id: Int,

    @SerializedName("imdb_id") @Expose
    val imdb_id: String?,

    @SerializedName("original_language") @Expose
    val original_language: String?,

    @SerializedName("original_title") @Expose
    val original_title: String?,

    @SerializedName("overview") @Expose
    val overview: String?,

    @SerializedName("popularity") @Expose
    val popularity: Double?,

    @SerializedName("poster_path") @Expose
    val poster_path: String?,

    @SerializedName("production_companies") @Expose
    val production_companies: List<ProductionCompany>?,

    @SerializedName("production_countries") @Expose
    val production_countries: List<ProductionCountry>?,

    @SerializedName("release_date") @Expose
    val release_date: String?,

    @SerializedName("revenue") @Expose
    val revenue: Int?,

    @SerializedName("runtime") @Expose
    val runtime: Int?,

    @SerializedName("spoken_languages") @Expose
    val spoken_languages: List<SpokenLanguage>?,

    @SerializedName("status") @Expose
    val status: String?,

    @SerializedName("tagline") @Expose
    val tagline: String?,

    @SerializedName("title") @Expose
    val title: String?,

    @SerializedName("video") @Expose
    val video: Boolean?,

    @SerializedName("vote_average") @Expose
    val vote_average: Double?,

    @SerializedName("vote_count") @Expose
    val vote_count: Int?
)
