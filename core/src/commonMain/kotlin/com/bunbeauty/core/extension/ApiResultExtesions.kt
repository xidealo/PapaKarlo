package com.bunbeauty.core.extension

import com.bunbeauty.core.ApiError
import com.bunbeauty.core.ApiResult
import com.bunbeauty.core.Logger
import com.bunbeauty.core.Logger.NETWORK_TAG

inline fun <T> ApiResult<T>.dataOrNull(): T? = if (this is ApiResult.Success) data else null

val <T> ApiResult<T>.isSuccess: Boolean
    get() = this is ApiResult.Success

suspend fun <T, R> ApiResult<T>.map(
    onError: (suspend (ApiError) -> R),
    onSuccess: (suspend (T?) -> R),
): R =
    when (this) {
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
): R? =
    when (this) {
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

