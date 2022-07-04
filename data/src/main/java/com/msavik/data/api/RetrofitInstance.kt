package com.msavik.data.api

import com.google.gson.GsonBuilder
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
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        val api: MovieAPI by lazy {
            retrofit.create(MovieAPI::class.java)
        }
    }
}