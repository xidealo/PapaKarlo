package com.bunbeauty.core.domain.order

import com.bunbeauty.core.model.order.LightOrder
import com.bunbeauty.core.model.order.OrderStatus

interface TakeInProgressLightOrderUseCase {
    operator fun invoke(lightOrder: LightOrder?): LightOrder?
}

class TakeInProgressLightOrderUseCaseImpl : TakeInProgressLightOrderUseCase {
    override operator fun invoke(lightOrder: LightOrder?): LightOrder? =
        lightOrder?.takeIf { order ->
            isOrderInProgress(order.status)
        }

    private fun isOrderInProgress(status: OrderStatus): Boolean =
        when (status) {
            OrderStatus.DELIVERED,
            OrderStatus.CANCELED,
            -> false

            else -> true
        }
}
