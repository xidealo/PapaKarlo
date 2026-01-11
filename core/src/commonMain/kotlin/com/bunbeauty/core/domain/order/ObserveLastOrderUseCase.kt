package com.bunbeauty.core.domain.order

import com.bunbeauty.core.model.order.LightOrder
import com.bunbeauty.core.domain.repo.OrderRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

class ObserveLastOrderUseCase(
    private val orderRepo: OrderRepo,
    private val lightOrderMapper: LightOrderMapper,
) {
    suspend operator fun invoke(): Pair<String?, Flow<LightOrder?>> {
        val lastOrder =
            orderRepo.getLastOrderByUserUuidNetworkFirst()

        return if (lastOrder == null) {
            null to flow { emit(null) }
        } else {
            val (uuid, orderUpdatesFlow) = orderRepo.observeOrderUpdates()
            uuid to
                merge(
                    flow { emit(lastOrder) },
                    orderUpdatesFlow
                        .filter { order ->
                            order.uuid == lastOrder.uuid
                        }.map(lightOrderMapper::toLightOrder),
                )
        }
    }
}
