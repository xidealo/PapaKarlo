package com.bunbeauty.papakarlo.feature.create_order.screen.create_order

import com.bunbeauty.papakarlo.feature.profile.screen.profile.PaymentMethodUiStateMapper
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.presentation.create_order.CreateOrderUIState

class CreateOrderStateMapper(
    private val stringUtil: IStringUtil,
    private val paymentMethodUiStateMapper: PaymentMethodUiStateMapper,
) {
    fun map(createOrderUIState: CreateOrderUIState): CreateOrderUi {
        return CreateOrderUi(
            isDelivery = createOrderUIState.isDelivery,
            deliveryAddress = stringUtil.getUserAddressString(createOrderUIState.deliveryAddress),
            pickupAddress = createOrderUIState.pickupAddress,
            comment = createOrderUIState.comment,
            deferredTime = stringUtil.getTimeString(createOrderUIState.deferredTime),
            totalCost = stringUtil.getCostString(createOrderUIState.totalCost),
            deliveryCost = stringUtil.getCostString(createOrderUIState.deliveryCost),
            finalCost = stringUtil.getCostString(createOrderUIState.finalCost),
            isAddressErrorShown = createOrderUIState.isDeliveryAddressErrorShown,
            isLoading = createOrderUIState.isLoading,
            selectedPaymentMethod = createOrderUIState.paymentMethod?.let {
                paymentMethodUiStateMapper.map(it)
            },
        )
    }
}
