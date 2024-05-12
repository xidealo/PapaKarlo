package com.bunbeauty.shared.presentation.create_order

class CreateOrderStateMapper {
    fun map(dataState: CreateOrderDataState): CreateOrderUIState {
        return CreateOrderUIState(
            isDelivery = dataState.isDelivery,
            deliveryAddress = dataState.selectedUserAddress,
            isDeliveryAddressErrorShown = dataState.isUserAddressErrorShown,
            pickupAddress = dataState.selectedCafe?.cafe?.address,
            comment = dataState.comment,
            deferredTime = dataState.deferredTime,

            totalCost = dataState.totalCost,
            deliveryCost = dataState.deliveryCost,
            oldFinalCost  = dataState.oldFinalCost,
            newFinalCost = dataState.newFinalCost,

            isLoading = dataState.isLoading,

            eventList = dataState.eventList,
            paymentMethod = dataState.selectedPaymentMethod,
            discount = dataState.discount
        )
    }
}