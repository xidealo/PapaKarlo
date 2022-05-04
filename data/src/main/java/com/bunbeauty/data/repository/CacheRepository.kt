package com.bunbeauty.data.repository

import com.bunbeauty.data.handleResultAndAlwaysReturn
import com.bunbeauty.data.network.ApiResult

abstract class CacheRepository<D> {

    abstract val tag: String
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
            onApiRequest().handleResultAndAlwaysReturn(
                tag = tag,
                onError = {
                    onLocalRequest()
                },
                onSuccess = { serverModel ->
                    onSaveLocally(serverModel)
                    serverToDomainModel(serverModel).also { domainModelList ->
                        cache = domainModelList
                    }
                }
            )
        }
    }
}