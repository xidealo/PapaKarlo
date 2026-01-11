package com.bunbeauty.core.domain.order

import com.bunbeauty.core.model.order.LightOrder
import com.bunbeauty.core.domain.repo.OrderRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge

class ObserveOrderListUseCase(
    private val orderRepo: OrderRepo,
) {
    suspend operator fun invoke(): Pair<String?, Flow<List<LightOrder>>> {

        val orderList = orderRepo.getOrderList()

        val (uuid, orderListUpdatesFlow) = orderRepo.observeLightOrderListUpdates()
        return uuid to
            merge(
                flow { emit(orderList) },
                orderListUpdatesFlow,
            )
    }
}
