package com.bunbeauty.data

import com.bunbeauty.common.Logger.logD
import com.bunbeauty.common.Logger.logE
import com.bunbeauty.data.network.ApiError
import com.bunbeauty.data.network.ApiResult
import com.bunbeauty.data.network.model.ListServer

suspend fun <T, R> ApiResult<T>.handleResultAndAlwaysReturn(
    tag: String,
    onError: (suspend (ApiError) -> R?),
    onSuccess: (suspend (T) -> R?)
): R? {
    logD(tag, "ApiResult = $this")
    return when (this) {
        is ApiResult.Success -> {
            data?.let { result ->
                onSuccess(result)
            } ?: onError(ApiError.DATA_IS_NULL)
        }
        is ApiResult.Error -> {
            onError(apiError)
        }
    }
}

suspend fun <T, R> ApiResult<T>.handleResultAndReturn(
    tag: String,
    onError: (suspend (ApiError) -> Unit)? = null,
    onSuccess: (suspend (T) -> R?)
): R? {
    when (this) {
        is ApiResult.Success -> {
            val result = data
            logD(tag, "ApiResult.Success $result")
            if (result != null) {
                return onSuccess(result)
            }
        }
        is ApiResult.Error -> {
            logE(tag, "ApiResult.Error $apiError")
            onError?.invoke(apiError)
        }
    }

    return null
}

suspend fun <T> ApiResult<T>.handleResult(
    tag: String,
    onError: (suspend (ApiError) -> Unit)? = null,
    onSuccess: (suspend (T) -> Unit)
) {
    when (this) {
        is ApiResult.Success -> {
            val result = data
            logD(tag, "ApiResult.Success $result")
            if (result != null) {
                onSuccess(result)
            }
        }
        is ApiResult.Error -> {
            logE(tag, "ApiResult.Error $apiError")
            onError?.invoke(apiError)
        }
    }
}

suspend fun <T> ApiResult<T>.handleNullableResult(
    tag: String,
    onError: (suspend (ApiError) -> Unit)? = null,
    onSuccess: (suspend (T?) -> Unit)
) {
    when (this) {
        is ApiResult.Success -> {
            logD(tag, "ApiResult.Success $data")
            onSuccess(data)
        }
        is ApiResult.Error -> {
            logE(tag, "ApiResult.Error $apiError")
            onError?.invoke(apiError)
        }
    }
}

suspend fun <T> ApiResult<ListServer<T>>.handleListResult(
    tag: String,
    onError: (suspend (ApiError) -> Unit)? = null,
    onSuccess: (suspend (List<T>?) -> Unit)
) {
    logD(tag, "ApiResult = $this")
    when (this) {
        is ApiResult.Success -> {
            onSuccess(data?.results)
        }
        is ApiResult.Error -> {
            onError?.invoke(apiError)
        }
    }
}

suspend fun <T, R> ApiResult<ListServer<T>>.handleListResultAndReturn(
    tag: String,
    onError: (suspend (ApiError) -> R),
    onSuccess: (suspend (List<T>) -> R)
): R {
    logD(tag, "ApiResult = $this")
    return when (this) {
        is ApiResult.Success -> {
            data?.let {
                onSuccess(data.results)
            } ?: onError(ApiError.DATA_IS_NULL)
        }
        is ApiResult.Error -> {
            onError(apiError)
        }
    }
}