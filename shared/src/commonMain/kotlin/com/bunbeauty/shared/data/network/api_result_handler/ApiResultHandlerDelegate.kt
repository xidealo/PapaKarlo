package com.bunbeauty.shared.data.network.api_result_handler

import com.bunbeauty.shared.data.network.ApiError
import com.bunbeauty.shared.data.network.ApiResult
import com.bunbeauty.shared.data.network.model.ListServer

class ApiResultHandlerDelegate : ApiResultHandler {

    override suspend fun <T, R> ApiResult<T>.getNullableResult(
        onError: (suspend (ApiError) -> R?)?,
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

    override suspend fun <T, R> ApiResult<ListServer<T>>.getListResult(
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


}