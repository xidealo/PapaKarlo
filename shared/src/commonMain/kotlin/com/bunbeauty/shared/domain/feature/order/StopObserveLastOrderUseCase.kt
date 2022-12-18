package com.bunbeauty.shared.domain.feature.order

import com.bunbeauty.shared.domain.repo.OrderRepo

class StopObserveLastOrderUseCase(
    private val orderRepo: OrderRepo
) {
    suspend operator fun invoke(){
        orderRepo.stopOrderUpdatesObservation()
    }
}