package com.bunbeauty.shared.presentation.create_order

import com.bunbeauty.shared.domain.model.address.SelectableUserAddress
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.model.payment_method.SelectablePaymentMethod
import com.bunbeauty.shared.presentation.cafe_address_list.SelectableCafeAddressItem

sealed interface CreateOrderEvent {
    data object OpenCreateAddressEvent : CreateOrderEvent
    data class ShowUserAddressListEvent(
        val addressList: List<SelectableUserAddress>,
    ) : CreateOrderEvent

    data class ShowCafeAddressListEvent(
        val addressList: List<SelectableCafeAddressItem>,
    ) : CreateOrderEvent

    data class ShowDeferredTimeEvent(
        val deferredTime: Time?,
        val minTime: Time,
        val isDelivery: Boolean,
    ) : CreateOrderEvent

    data class ShowCommentInputEvent(val comment: String?) : CreateOrderEvent
    data object ShowUserUnauthorizedErrorEvent : CreateOrderEvent
    data object ShowSomethingWentWrongErrorEvent : CreateOrderEvent
    data class OrderCreatedEvent(val code: String) : CreateOrderEvent
    data object ShowUserAddressError : CreateOrderEvent
    data object ShowPaymentMethodError : CreateOrderEvent
    data class ShowPaymentMethodList(val selectablePaymentMethodList: List<SelectablePaymentMethod>) :
        CreateOrderEvent
}