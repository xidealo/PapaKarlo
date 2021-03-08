package com.bunbeauty.papakarlo.data.local.db.delivery

import com.bunbeauty.papakarlo.data.api.firebase.IApiRepository
import com.bunbeauty.papakarlo.data.local.datastore.IDataStoreHelper
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