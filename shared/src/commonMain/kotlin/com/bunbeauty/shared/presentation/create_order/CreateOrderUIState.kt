package com.bunbeauty.shared.presentation.create_order

import com.bunbeauty.shared.domain.model.address.SelectableUserAddress
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethod

data class CreateOrderUIState(
    val isDelivery: Boolean,
    val deliveryAddress: SelectableUserAddress?,
    val isDeliveryAddressErrorShown: Boolean,
    val pickupAddress: String?,
    val comment: String?,
    val deferredTime: Time?,

    val totalCost: Int?,
    val deliveryCost: Int?,
    val oldFinalCost: Int?,
    val newFinalCost: Int?,

    val isLoading: Boolean,
    val eventList: List<CreateOrderEvent>,
    val paymentMethod: PaymentMethod?,
    val discount: String?,
)