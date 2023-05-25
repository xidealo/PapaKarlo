package com.bunbeauty.shared.presentation.create_order

import com.bunbeauty.shared.presentation.cafe_address_list.SelectableCafeAddressItem
import com.bunbeauty.shared.presentation.create_order.model.SelectableUserAddressUi
import com.bunbeauty.shared.presentation.create_order.model.TimeUI

sealed interface CreateOrderEvent {
    object OpenCreateAddressEvent : CreateOrderEvent
    data class ShowUserAddressListEvent(
        val addressList: List<SelectableUserAddressUi>,
    ) : CreateOrderEvent

    data class ShowCafeAddressListEvent(
        val addressList: List<SelectableCafeAddressItem>,
    ) : CreateOrderEvent

    data class ShowDeferredTimeEvent(
        val deferredTime: TimeUI,
        val minTime: TimeUI.Time,
        val isDelivery: Boolean,
    ) : CreateOrderEvent

    data class ShowCommentInputEvent(val comment: String?) : CreateOrderEvent
    object ShowUserUnauthorizedErrorEvent : CreateOrderEvent
    object ShowSomethingWentWrongErrorEvent : CreateOrderEvent
    data class OrderCreatedEvent(val code: String) : CreateOrderEvent
    object ShowUserAddressError : CreateOrderEvent
}