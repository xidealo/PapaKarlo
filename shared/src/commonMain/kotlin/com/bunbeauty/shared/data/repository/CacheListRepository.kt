package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.network.ApiResult
import com.bunbeauty.shared.data.network.model.ListServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

abstract class CacheListRepository<D> : BaseRepository() {

    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

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
                    scope.launch {
                        onSaveLocally(serverModelList)
                    }
                    serverModelList.map { serverModel ->
                        serverToDomainModel(serverModel)
                    }.also { domainModelList ->
                        cache = domainModelList
                    }
                }
            )
        }
    }

    protected fun updateCache(block: (List<D>?) -> List<D>?) {
        cache = block(cache)
    }
}
