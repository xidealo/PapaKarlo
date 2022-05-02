package com.bunbeauty.data.repository

import com.bunbeauty.data.network.ApiResult
import com.bunbeauty.data.network.model.ListServer

abstract class CacheListRepository<D>: BaseRepository() {

    protected var cache: List<D>? = null

    protected suspend inline fun <S> getCacheOrListData(
        isCacheValid: ((List<D>) -> Boolean) = { true },
        crossinline onApiRequest: suspend () -> ApiResult<ListServer<S>>,
        crossinline onLocalRequest: suspend () -> List<D>,
        crossinline onSaveLocally: suspend (List<S>) -> Unit,
        crossinline serverToDomainModel: (S) -> D,
    ): List<D> {
        val cacheData = cache
        return if (cacheData != null && isCacheValid(cacheData)) {
            cacheData
        } else {
            onApiRequest().getListResult(
                onError = {
                    onLocalRequest()
                },
                onSuccess = { serverModelList ->
                    onSaveLocally(serverModelList)
                    serverModelList.map { serverModel ->
                        serverToDomainModel(serverModel)
                    }.also { domainModelList ->
                        cache = domainModelList
                    }
                }
            )
        }
    }
}