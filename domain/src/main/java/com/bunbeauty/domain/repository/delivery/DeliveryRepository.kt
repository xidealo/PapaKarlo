package com.bunbeauty.domain.repository.delivery

import com.bunbeauty.data.utils.IDataStoreHelper
import com.bunbeauty.data.api.IApiRepository
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class DeliveryRepository @Inject constructor(
    private val apiRepository: IApiRepository,
    private val dataStoreHelper: IDataStoreHelper
) : DeliveryRepo {

    @InternalCoroutinesApi
    override suspend fun refreshDeliveryCost() {
        apiRepository.getDeliveryCost().collect { delivery ->
            dataStoreHelper.saveDelivery(delivery)
        }
    }
}