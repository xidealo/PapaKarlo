package com.bunbeauty.shared.data.repository

import com.bunbeauty.core.ApiError
import com.bunbeauty.core.ApiResult
import com.bunbeauty.shared.data.network.model.ListServer

abstract class BaseRepository {
    abstract val tag: String

    suspend fun <T, R> ApiResult<T>.getNullableResult(
        onError: (suspend (ApiError) -> R?)? = null,
        onSuccess: (suspend (T) -> R?),
    ): R? =
        when (this) {
            is ApiResult.Success -> {
                data?.let {
                    onSuccess(it)
                } ?: onError?.invoke(ApiError.DATA_IS_NULL)
            }
            is ApiResult.Error -> {
                onError?.invoke(apiError)
            }
        }

    suspend fun <T, R> ApiResult<ListServer<T>>.getListResult(
        onError: (suspend (ApiError) -> R),
        onSuccess: (suspend (List<T>) -> R),
    ): R =
        when (this) {
            is ApiResult.Success -> {
                data?.let {
                    onSuccess(it.results)
                } ?: onError(ApiError.DATA_IS_NULL)
            }
            is ApiResult.Error -> {
                onError(apiError)
            }
        }
}

