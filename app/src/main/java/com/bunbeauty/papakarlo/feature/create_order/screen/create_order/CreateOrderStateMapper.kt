package com.bunbeauty.papakarlo.feature.create_order.screen.create_order

import com.bunbeauty.papakarlo.util.string.StringUtil
import com.bunbeauty.shared.presentation.create_order.CreateOrderState

class CreateOrderStateMapper(
    private val stringUtil: StringUtil,
) {
    fun map(createOrderState: CreateOrderState): CreateOrderUi {
        return CreateOrderUi(
            isDelivery = createOrderState.isDelivery,
            deliveryAddress = stringUtil.getUserAddressString(createOrderState.deliveryAddress),
            pickupAddress = createOrderState.pickupAddress,
            comment = createOrderState.comment,
            deferredTime = stringUtil.getTimeString(createOrderState.deferredTime),
            totalCost = stringUtil.getCostString(createOrderState.totalCost),
            deliveryCost = stringUtil.getCostString(createOrderState.deliveryCost),
            finalCost = stringUtil.getCostString(createOrderState.finalCost),
            isAddressErrorShown = createOrderState.isAddressErrorShown,
            isLoading = createOrderState.isLoading,
        )
    }
}
