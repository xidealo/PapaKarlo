package com.bunbeauty.core.domain.order

import com.bunbeauty.core.domain.repo.OrderRepo

class StopObserveOrdersUseCase(
    private val orderRepo: OrderRepo,
) {
    suspend operator fun invoke(uuid: String) {
        orderRepo.stopOrderUpdatesObservation(uuid)
    }
}
