package com.bunbeauty.shared.presentation.create_order

import com.bunbeauty.shared.domain.model.address.SelectableUserAddress
import com.bunbeauty.shared.domain.model.cafe.SelectableCafe
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethod
import com.bunbeauty.shared.domain.model.payment_method.SelectablePaymentMethod

data class CreateOrderDataState(
    val isDelivery: Boolean = true,

    val userAddressList: List<SelectableUserAddress> = emptyList(),
    val selectedUserAddress: SelectableUserAddress? = null,
    val isUserAddressErrorShown: Boolean = false,

    val cafeList: List<SelectableCafe> = emptyList(),
    val selectedCafe: SelectableCafe? = null,

    val comment: String? = null,
    val deferredTime: Time? = null,

    val totalCost: Int? = null,
    val deliveryCost: Int? = null,
    val oldFinalCost: Int? = null,
    val newFinalCost: Int? = null,

    val isLoading: Boolean = false,
    val eventList: List<CreateOrderEvent> = emptyList(),
    val selectedPaymentMethod: PaymentMethod? = null,
    val paymentMethodList: List<SelectablePaymentMethod> = emptyList(),
    val discount: String?,
) {

    operator fun plus(event: CreateOrderEvent) = copy(eventList = eventList + event)
    operator fun minus(events: List<CreateOrderEvent>) =
        copy(eventList = eventList - events.toSet())

}
