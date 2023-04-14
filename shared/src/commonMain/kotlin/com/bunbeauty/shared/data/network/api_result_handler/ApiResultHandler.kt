package com.bunbeauty.shared.data.network.api_result_handler

import com.bunbeauty.shared.data.network.ApiError
import com.bunbeauty.shared.data.network.ApiResult
import com.bunbeauty.shared.data.network.model.ListServer

interface ApiResultHandler {

    suspend fun <T, R> ApiResult<T>.getNullableResult(
        onError: (suspend (ApiError) -> R?)? = null,
        onSuccess: (suspend (T) -> R?)
    ): R?

    suspend fun <T, R> ApiResult<ListServer<T>>.getListResult(
        onError: (suspend (ApiError) -> R),
        onSuccess: (suspend (List<T>) -> R)
    ): R

}