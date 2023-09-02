package com.bunbeauty.shared.presentation.create_order

import com.bunbeauty.shared.data.mapper.user_address.UserAddressMapper

class CreateOrderStateMapper(
    private val userAddressMapper: UserAddressMapper,
    private val timeMapper: TimeMapper,
) {
    fun map(dataState: CreateOrderDataState): CreateOrderUIState {
        return CreateOrderUIState(
            isDelivery = dataState.isDelivery,
            deliveryAddress = userAddressMapper.toUiModel(dataState.selectedUserAddress),
            isDeliveryAddressErrorShown = dataState.isUserAddressErrorShown,
            pickupAddress = dataState.selectedCafe?.address,
            comment = dataState.comment,
            deferredTime = timeMapper.toUiModel(dataState.deferredTime),

            totalCost = dataState.totalCost,
            deliveryCost = dataState.deliveryCost,
            finalCost = dataState.finalCost,

            isLoading = dataState.isLoading,

            eventList = dataState.eventList,
            paymentMethod = dataState.selectedPaymentMethod,
            discount = dataState.discount
        )
    }
}