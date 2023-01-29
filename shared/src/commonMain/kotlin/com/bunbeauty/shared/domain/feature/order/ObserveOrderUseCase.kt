package com.bunbeauty.shared.domain.feature.order

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.order.Order
import com.bunbeauty.shared.domain.repo.OrderRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge

class ObserveOrderUseCase(
    private val dataStoreRepo: DataStoreRepo,
    private val orderRepo: OrderRepo
) {

    suspend operator fun invoke(orderUuid: String): Pair<String?, Flow<Order?>> {
        val token = dataStoreRepo.getToken() ?: return null to flow {}
        val order = orderRepo.getOrderByUuid(token = token, orderUuid = orderUuid)

        return if (order == null) {
            null to flow { emit(null) }
        } else {
            val (uuid, orderUpdatesFlow) = orderRepo.observeOrderUpdates(token)
            uuid to merge(
                flow { emit(order) },
                orderUpdatesFlow.filter { orderUpdate ->
                    orderUpdate.uuid == order.uuid
                }
            )
        }
    }
}