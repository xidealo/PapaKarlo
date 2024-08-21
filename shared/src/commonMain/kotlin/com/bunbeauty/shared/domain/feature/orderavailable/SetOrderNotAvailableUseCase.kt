package com.bunbeauty.shared.domain.feature.orderavailable

import com.bunbeauty.shared.domain.model.order.OrderAvailability
import com.bunbeauty.shared.domain.repo.OrderAvailableRepo

class SetOrderNotAvailableUseCase(
    private val orderAvailableRepository: OrderAvailableRepo
) {
    operator fun invoke() {
        orderAvailableRepository.update(
            orderAvailability = OrderAvailability(
                available = false
            )
        )
    }
}
