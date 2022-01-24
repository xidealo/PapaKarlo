package com.example.data_api.repository

import com.bunbeauty.common.ApiError
import com.bunbeauty.common.ApiResult
import com.example.data_api.handleNullableResult
import com.example.data_api.handleResult
import com.example.data_api.handleResultAndReturn

abstract class BaseRepository {

    protected val tag: String =
        javaClass.simpleName.substring(javaClass.simpleName.lastIndexOf('.') + 1)

    suspend fun <T, R> ApiResult<T>.handleResultAndReturn(
        onError: (suspend (ApiError) -> Unit)? = null,
        onSuccess: (suspend (T) -> R)
    ): R? {
        return handleResultAndReturn(tag, onError, onSuccess)
    }

    suspend fun <T> ApiResult<T>.handleResult(
        onError: (suspend (ApiError) -> Unit)? = null,
        onSuccess: (suspend (T) -> Unit)
    ) {
        handleResult(tag, onError, onSuccess)
    }

    suspend fun <T> ApiResult<T>.handleNullableResult(
        onError: (suspend (ApiError) -> Unit)? = null,
        onSuccess: (suspend (T?) -> Unit)
    ) {
        handleNullableResult(tag, onError, onSuccess)
    }
}