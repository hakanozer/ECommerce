package com.works.domain

sealed interface ApiResult<out T> {

    data class Success<T>(
        val data: T
    ) : ApiResult<T>

    data class Error(
        val code: Int,
        val message: String
    ) : ApiResult<Nothing>

    data class NetworkError(
        val message: String
    ) : ApiResult<Nothing>
}