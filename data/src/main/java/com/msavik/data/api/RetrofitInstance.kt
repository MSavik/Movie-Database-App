package com.msavik.data.api

import com.google.gson.GsonBuilder
import com.msavik.data.utility.BaseUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            val client = OkHttpClient
                .Builder()
                .build()
            val gson = GsonBuilder()
                .setLenient()
                .create()
            Retrofit.Builder()
                .client(client)
                .baseUrl(BaseUrl.TMDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        val api: MovieAPI by lazy {
            retrofit.create(MovieAPI::class.java)
        }
    }
}