package com.bunbeauty.data.repository

import com.bunbeauty.data.handleNullableResult
import com.bunbeauty.data.handleResult
import com.bunbeauty.data.handleResultAndReturn
import com.bunbeauty.data.network.ApiError
import com.bunbeauty.data.network.ApiResult

abstract class BaseRepository {

    private val tag: String =
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