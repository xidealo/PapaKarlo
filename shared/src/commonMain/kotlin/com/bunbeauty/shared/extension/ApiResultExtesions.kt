package com.bunbeauty.shared.extension

import com.bunbeauty.shared.data.network.ApiError
import com.bunbeauty.shared.data.network.ApiResult
import com.bunbeauty.shared.data.network.model.ListServer

suspend fun <T, R> ApiResult<T>.getNullableResult(
    onError: (suspend (ApiError) -> R?)? = null,
    onSuccess: (suspend (T) -> R?)
): R? = when (this) {
    is ApiResult.Success -> {
        data?.let {
            onSuccess(data)
        } ?: onError?.invoke(ApiError.DATA_IS_NULL)
    }
    is ApiResult.Error -> {
        onError?.invoke(apiError)
    }
}

suspend fun <T, R> ApiResult<ListServer<T>>.getListResult(
    onError: (suspend (ApiError) -> R),
    onSuccess: (suspend (List<T>) -> R)
): R = when (this) {
    is ApiResult.Success -> {
        data?.let {
            onSuccess(data.results)
        } ?: onError(ApiError.DATA_IS_NULL)
    }
    is ApiResult.Error -> {
        onError(apiError)
    }
}
