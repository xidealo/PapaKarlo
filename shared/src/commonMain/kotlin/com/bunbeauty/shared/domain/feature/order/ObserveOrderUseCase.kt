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

    suspend operator fun invoke(orderUuid: String): Flow<Order?> {
        val token = dataStoreRepo.getToken() ?: return flow {}
        val order = orderRepo.getOrderByUuid(orderUuid = orderUuid)

        return if (order == null) {
            flow { emit(null) }
        } else {
            merge(
                flow { emit(order) },
                orderRepo.observeOrderUpdates(token).filter { orderUpdate ->
                    orderUpdate.uuid == order.uuid
                }
            )
        }
    }
}