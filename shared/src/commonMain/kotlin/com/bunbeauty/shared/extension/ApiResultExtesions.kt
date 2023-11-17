package com.bunbeauty.shared.extension

import com.bunbeauty.core.Logger
import com.bunbeauty.core.Logger.NETWORK_TAG
import com.bunbeauty.shared.data.network.ApiError
import com.bunbeauty.shared.data.network.ApiResult
import com.bunbeauty.shared.data.network.model.ListServer

inline fun <T> ApiResult<T>.dataOrNull(): T? = if (this is ApiResult.Success) data else null

val <T> ApiResult<T>.isSuccess: Boolean
    get() = this is ApiResult.Success

suspend fun <T, R> ApiResult<T>.map(
    onError: (suspend (ApiError) -> R),
    onSuccess: (suspend (T?) -> R),
): R = when (this) {
    is ApiResult.Success -> {
        onSuccess(data)
    }

    is ApiResult.Error -> {
        onError(apiError)
    }
}

suspend fun <T, R> ApiResult<T>.getNullableResult(
    onError: (suspend (ApiError) -> R?)? = null,
    onSuccess: (suspend (T) -> R?),
): R? = when (this) {
    is ApiResult.Success -> {
        data?.let {
            onSuccess(data)
        }
    }

    is ApiResult.Error -> {
        Logger.logW(NETWORK_TAG, apiError.message)
        onError?.invoke(apiError)
    }
}

suspend fun <T, R> ApiResult<ListServer<T>>.getListResult(
    onError: (suspend (ApiError) -> R),
    onSuccess: (suspend (List<T>) -> R),
): R = when (this) {
    is ApiResult.Success -> {
        data?.let {
            onSuccess(data.results)
        } ?: onError(ApiError.DATA_IS_NULL)
    }

    is ApiResult.Error -> {
        Logger.logW(NETWORK_TAG, apiError.message)
        onError(apiError)
    }
}
