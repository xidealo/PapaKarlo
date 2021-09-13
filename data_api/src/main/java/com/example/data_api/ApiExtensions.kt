package com.example.data_api

import com.bunbeauty.common.ApiError
import com.bunbeauty.common.ApiResult
import com.bunbeauty.common.Logger.logD
import com.bunbeauty.common.Logger.logE

suspend fun <T> ApiResult<T>.handleResult(
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