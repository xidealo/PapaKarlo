package com.bunbeauty.domain.repository.delivery

import com.bunbeauty.data.utils.IDataStoreHelper
import com.bunbeauty.domain.repository.api.IApiRepository
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class DeliveryRepository @Inject constructor(
    private val apiRepository: IApiRepository,
    private val dataStoreHelper: IDataStoreHelper
) : DeliveryRepo {

    @InternalCoroutinesApi
    override suspend fun refreshDeliveryCost() {
        apiRepository.getDelivery().collect { delivery ->
            dataStoreHelper.saveDelivery(delivery)
        }
    }
}