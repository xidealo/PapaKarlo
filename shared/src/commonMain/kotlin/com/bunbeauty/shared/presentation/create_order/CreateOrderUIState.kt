package com.bunbeauty.shared.presentation.create_order

import com.bunbeauty.shared.presentation.create_order.model.SelectableUserAddressUi
import com.bunbeauty.shared.presentation.create_order.model.TimeUI

data class CreateOrderUIState(
    val isDelivery: Boolean,
    val deliveryAddress: SelectableUserAddressUi?,
    val isDeliveryAddressErrorShown: Boolean,
    val pickupAddress: String?,
    val comment: String?,
    val deferredTime: TimeUI,

    val totalCost: Int?,
    val deliveryCost: Int?,
    val finalCost: Int?,

    val isLoading: Boolean,
    val eventList: List<CreateOrderEvent>,
)