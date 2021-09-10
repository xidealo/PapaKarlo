package com.bunbeauty.common

sealed class ApiResult<out T : Any> {
    data class Success<out T : Any>(val data: T?) : ApiResult<T>()
    data class Error(val apiError: ApiError) : ApiResult<Nothing>()
}