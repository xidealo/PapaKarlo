package com.bunbeauty.shared.domain.feature.order

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.repo.OrderRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

class ObserveLastOrderUseCase(
    private val dataStoreRepo: DataStoreRepo,
    private val orderRepo: OrderRepo,
    private val lightOrderMapper: LightOrderMapper,
) {

    suspend operator fun invoke(): Flow<LightOrder?> {
        val token = dataStoreRepo.getToken() ?: return flow {}
        val userUuid = dataStoreRepo.getUserUuid() ?: return flow {}
        val lastOrder = orderRepo.getLastOrderByUserUuid(token = token, userUuid = userUuid)

        return if (lastOrder == null) {
            flow { emit(null) }
        } else {
            merge(
                flow { emit(lastOrder) },
                orderRepo.observeOrderUpdates(token).filter { order ->
                    order.uuid == lastOrder.uuid
                }.map(lightOrderMapper::toLightOrder)
            )
        }
    }
}