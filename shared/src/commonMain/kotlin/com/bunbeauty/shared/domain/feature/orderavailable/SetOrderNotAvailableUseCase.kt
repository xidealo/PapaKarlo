package com.bunbeauty.shared.domain.feature.orderavailable

import com.bunbeauty.shared.data.repository.OrderAvailableRepository
import com.bunbeauty.shared.domain.model.order.OrderAvailability

class SetOrderNotAvailableUseCase(
    private val orderAvailableRepository: OrderAvailableRepository,
) {
    operator fun invoke() {
        orderAvailableRepository.update(
            orderAvailability = OrderAvailability(
                available = false
            )
        )
    }
}