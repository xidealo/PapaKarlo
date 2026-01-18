package com.bunbeauty.shared.data.repository

import com.bunbeauty.core.ApiResult

abstract class CacheRepository<D> : BaseRepository() {
    protected var cache: D? = null

    protected suspend inline fun <S> getCacheOrData(
        isCacheValid: ((D) -> Boolean) = { true },
        crossinline onApiRequest: suspend () -> ApiResult<S>,
        crossinline onLocalRequest: suspend () -> D?,
        crossinline onSaveLocally: suspend (S) -> Unit,
        crossinline serverToDomainModel: (S) -> D,
    ): D? {
        val cacheData = cache
        return if (cacheData != null && isCacheValid(cacheData)) {
            cacheData
        } else {
            onApiRequest().getNullableResult(
                onError = {
                    onLocalRequest()
                },
                onSuccess = { serverModel ->
                    onSaveLocally(serverModel)
                    serverToDomainModel(serverModel).also { domainModelList ->
                        cache = domainModelList
                    }
                },
            )
        }
    }
}
