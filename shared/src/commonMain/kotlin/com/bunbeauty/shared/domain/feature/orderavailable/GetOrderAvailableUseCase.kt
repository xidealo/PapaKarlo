package com.bunbeauty.shared.domain.feature.orderavailable

import com.bunbeauty.shared.data.repository.OrderAvailableRepository

private const val ORDER_AVAILABLE = true

class GetOrderAvailableUseCase(
    private val orderAvailableRepository: OrderAvailableRepository,
) {
    suspend operator fun invoke(): Boolean {
        return orderAvailableRepository.getOrderAvailable()?.available ?: ORDER_AVAILABLE
    }
}