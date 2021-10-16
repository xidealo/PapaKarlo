package com.example.data_api

import com.bunbeauty.common.ApiError
import com.bunbeauty.common.ApiResult
import com.bunbeauty.common.Logger.logD
import com.bunbeauty.common.Logger.logE
import com.example.domain_api.model.server.ListServer
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

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
    when (this) {
        is ApiResult.Success -> {
            logD(tag, "ApiResult.Success $data")
            onSuccess(data?.results)
        }
        is ApiResult.Error -> {
            logE(tag, "ApiResult.Error $apiError")
            onError?.invoke(apiError)
        }
    }
}

fun <MS, M> Flow<List<MS>>.mapListFlow(toModel: ((MS) -> M)): Flow<List<M>> {
    return flowOn(IO)
        .map { userAddressList ->
            userAddressList.map { modelServer ->
                toModel(modelServer)
            }
        }
        .flowOn(Default)
}

fun <MI, MO> Flow<MI?>.mapFlow(toModel: ((MI) -> MO)): Flow<MO?> {
    return flowOn(IO)
        .map { modelServer ->
            modelServer?.let {
                toModel(modelServer)
            }
        }
        .flowOn(Default)
}