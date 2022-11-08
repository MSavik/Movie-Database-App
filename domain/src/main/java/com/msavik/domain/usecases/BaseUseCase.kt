package com.msavik.domain.usecases

abstract class BaseUseCase<in Params, out Results> {
    abstract suspend fun execute(params: Params? = null): Results

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName
    }
}