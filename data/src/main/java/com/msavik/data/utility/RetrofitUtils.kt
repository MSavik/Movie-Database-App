package com.msavik.data.utility

import com.msavik.data.exception.NetworkOperationException
import retrofit2.Call
import retrofit2.Response

object RetrofitUtils {

    @Throws(NetworkOperationException::class)
    fun <T> execute(call: Call<T>): Response<T> {
        return executeCall(call)
    }

    private fun <T> executeCall(call: Call<T>): Response<T> {
        try {
            return call.execute()
        } catch (e: Exception) {
            throw NetworkOperationException(e.message ?: e.toString())
        }
    }

    fun <T> Response<T>.checkAndParseResponse(): T {
        if (this.isSuccessful && this.body() != null) {
            return this.body()!!
        } else {
            throw NetworkOperationException("Network error [${this.code()}] : ${this.errorBody()}")
        }
    }
}