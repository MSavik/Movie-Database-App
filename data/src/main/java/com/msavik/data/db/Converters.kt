package com.msavik.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.msavik.domain.model.movie.*

class Converters {

    @TypeConverter
    fun fromMovieCollection(movieCollection: MovieCollection?): String? {
        return movieCollection?.name
    }

    @TypeConverter
    fun toMovieCollection(name: String?): MovieCollection {
        return MovieCollection(0, name, "", "")
    }

    @TypeConverter
    fun fromMovieGenreList(genres: List<MovieGenre>?): String? {
        return Gson().toJson(genres)
    }

    @TypeConverter
    fun toMovieGenreList(movieGenre: MovieGenre?): List<MovieGenre>? {
        val listType = object : TypeToken<List<MovieGenre>>(){}.type
        return Gson().fromJson(fromMovieGenre(movieGenre), listType)
    }

    @TypeConverter
    fun fromProductionCompanyList(productionCompanies: List<ProductionCompany>?): String? {
        return Gson().toJson(productionCompanies)
    }

    @TypeConverter
    fun fromMovieGenre(movieGenre: MovieGenre?): String? {
        return movieGenre?.name
    }

    @TypeConverter
    fun toMovieGenre(name: String): MovieGenre {
        return MovieGenre(0, name)
    }

    @TypeConverter
    fun toProductionCompanyList(productionCompany: ProductionCompany?): List<ProductionCompany>? {
        val listType = object : TypeToken<List<ProductionCompany>>(){}.type
        return Gson().fromJson(fromProductionCompany(productionCompany), listType)
    }

    @TypeConverter
    fun fromProductionCompany(productionCompany: ProductionCompany?): String? {
        return productionCompany?.name
    }

    @TypeConverter
    fun toProductionCompany(name: String): ProductionCompany {
        return ProductionCompany(0, "", name, "")
    }

    @TypeConverter
    fun fromProductionCountryList(productionCountries: List<ProductionCountry>?): String? {
        return Gson().toJson(productionCountries)
    }

    @TypeConverter
    fun toProductionCountryList(productionCountry: ProductionCountry?): List<ProductionCountry>? {
        val listType = object : TypeToken<List<ProductionCountry>>(){}.type
        return Gson().fromJson(fromProductionCountry(productionCountry), listType)
    }

    @TypeConverter
    fun fromProductionCountry(productionCountry: ProductionCountry?): String? {
        return productionCountry?.name
    }

    @TypeConverter
    fun toProductionCountry(name: String): ProductionCountry {
        return ProductionCountry("", name)
    }

    @TypeConverter
    fun fromSpokenLanguageList(spokenLanguages: List<SpokenLanguage>?): String? {
        return Gson().toJson(spokenLanguages)
    }

    @TypeConverter
    fun toSpokenLanguageList(spokenLanguage: SpokenLanguage?): List<SpokenLanguage>? {
        val listType = object : TypeToken<List<SpokenLanguage>>(){}.type
        return Gson().fromJson(fromSpokenLanguage(spokenLanguage), listType)
    }

    @TypeConverter
    fun fromSpokenLanguage(spokenLanguage: SpokenLanguage?): String? {
        return spokenLanguage?.name
    }

    @TypeConverter
    fun toSpokenLanguage(name: String): SpokenLanguage {
        return SpokenLanguage("", "", name)
    }
}