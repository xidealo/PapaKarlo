package com.bunbeauty.shared.data.repository

import com.bunbeauty.core.ApiResult

abstract class DatabaseCacheRepository : BaseRepository() {
    protected suspend inline fun <S, D> getCacheOrData(
        isCacheValid: () -> Boolean,
        crossinline onLocalRequest: suspend () -> D?,
        crossinline onApiRequest: suspend () -> ApiResult<S>,
        crossinline onSaveLocally: suspend (S) -> Unit,
        crossinline serverToDomainModel: (S) -> D,
    ): D? =
        if (isCacheValid()) {
            onLocalRequest() ?: getDataFromServer(onApiRequest, onSaveLocally, serverToDomainModel)
        } else {
            getDataFromServer(
                onApiRequest,
                onSaveLocally,
                serverToDomainModel,
            )
        }

    suspend inline fun <S, D> getDataFromServer(
        crossinline onApiRequest: suspend () -> ApiResult<S>,
        crossinline onSaveLocally: suspend (S) -> Unit,
        crossinline serverToDomainModel: (S) -> D,
    ): D? =
        onApiRequest().getNullableResult(
            onError = {
                null
            },
            onSuccess = { serverModel ->
                onSaveLocally(serverModel)
                serverToDomainModel(serverModel)
            },
        )
}
