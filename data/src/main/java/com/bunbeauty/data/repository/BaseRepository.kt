package com.bunbeauty.data.repository

import com.bunbeauty.common.Logger.logD
import com.bunbeauty.common.Logger.logE
import com.bunbeauty.data.network.ApiError
import com.bunbeauty.data.network.ApiResult
import com.bunbeauty.data.network.model.ListServer

abstract class BaseRepository {

    abstract val tag: String

    suspend fun <T, R> ApiResult<T>.getNullableResult(
        onError: (suspend (ApiError) -> R?)? = null,
        onSuccess: (suspend (T) -> R?)
    ): R? {
        logD(tag, "ApiResult = $this")
        return when (this) {
            is ApiResult.Success -> {
                data?.let {
                    onSuccess(data)
                } ?: onError?.invoke(ApiError.DATA_IS_NULL)
            }
            is ApiResult.Error -> {
                onError?.invoke(apiError)
            }
        }
    }

    suspend fun <T, R> ApiResult<ListServer<T>>.getListResult(
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
}