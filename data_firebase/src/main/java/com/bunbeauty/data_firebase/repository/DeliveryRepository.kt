package com.bunbeauty.data_firebase.repository

import com.bunbeauty.domain.model.Delivery
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.DeliveryRepo
import com.example.domain_firebase.repo.FirebaseRepo
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class DeliveryRepository @Inject constructor(
    private val firebaseRepo: FirebaseRepo,
    private val dataStoreRepo: DataStoreRepo
) : DeliveryRepo {

    override suspend fun refreshDelivery() {
        firebaseRepo.getDelivery().collect { deliveryFirebase ->
            deliveryFirebase?.let {
                dataStoreRepo.saveDelivery(
                    Delivery(deliveryFirebase.cost, deliveryFirebase.forFree)
                )
            }
        }
    }

}