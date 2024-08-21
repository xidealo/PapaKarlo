package com.bunbeauty.shared.domain.feature.orderavailable

import com.bunbeauty.shared.domain.repo.OrderAvailableRepo

private const val DEFAULT_IS_ORDER_AVAILABLE = true

class IsOrderAvailableUseCase(
    private val orderAvailableRepository: OrderAvailableRepo
) {
    suspend operator fun invoke(): Boolean {
        return orderAvailableRepository.getOrderAvailable()?.available ?: DEFAULT_IS_ORDER_AVAILABLE
    }
}
