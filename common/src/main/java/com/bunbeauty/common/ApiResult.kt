package com.bunbeauty.common

sealed class ApiResult<T> {
    data class Success<T>(val data: T?) : ApiResult<T>()
    data class Error<T>(val apiError: ApiError) : ApiResult<T>()
}