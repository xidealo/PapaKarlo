package com.bunbeauty.core.domain.order

import com.bunbeauty.core.model.order.LightOrder
import com.bunbeauty.core.domain.repo.OrderRepo

class GetLastOrderUseCase(
    private val orderRepo: OrderRepo,
) {
    suspend operator fun invoke(): LightOrder? {
        return orderRepo.getLastOrderByUserUuidLocalFirst()
    }
}
