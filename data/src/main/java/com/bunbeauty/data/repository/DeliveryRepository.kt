package com.bunbeauty.data.repository

import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.ApiRepo
import com.bunbeauty.domain.repo.DeliveryRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DeliveryRepository @Inject constructor(
    private val apiRepo: ApiRepo,
    private val dataStoreRepo: DataStoreRepo
) : DeliveryRepo, CoroutineScope {

    override val coroutineContext: CoroutineContext = Job()

    override suspend fun refreshDeliveryCost() {
        apiRepo.getDelivery().onEach { delivery ->
            dataStoreRepo.saveDelivery(delivery)
        }.launchIn(this)
    }
}