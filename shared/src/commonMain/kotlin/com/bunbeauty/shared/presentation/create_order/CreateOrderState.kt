package com.bunbeauty.shared.presentation.create_order

import com.bunbeauty.shared.presentation.cafe_address_list.SelectableCafeAddressItem
import com.bunbeauty.shared.presentation.create_order.model.SelectableUserAddressUi
import com.bunbeauty.shared.presentation.create_order.model.TimeUI

data class CreateOrderState(
    val isDelivery: Boolean = true,
    val deliveryAddress: SelectableUserAddressUi? = null,
    val pickupAddress: String? = null,
    val comment: String? = null,
    val deferredTime: TimeUI = TimeUI.ASAP,
    val totalCost: Int? = null,
    val deliveryCost: Int? = null,
    val finalCost: Int? = null,
    val isAddressErrorShown: Boolean = false,
    val isLoading: Boolean = false,
    val eventList: List<Event> = emptyList(),
) {

    sealed interface Event {
        object OpenCreateAddressEvent : Event
        data class ShowUserAddressListEvent(
            val addressList: List<SelectableUserAddressUi>,
        ) : Event

        data class ShowCafeAddressListEvent(
            val addressList: List<SelectableCafeAddressItem>,
        ) : Event

        data class ShowDeferredTimeEvent(
            val deferredTime: TimeUI,
            val minTime: TimeUI.Time,
            val isDelivery: Boolean,
        ) : Event

        data class ShowCommentInputEvent(val comment: String?) : Event
        object ShowUserUnauthorizedErrorEvent : Event
        object ShowSomethingWentWrongErrorEvent : Event
        data class OrderCreatedEvent(val code: String) : Event
        object ShowUserAddressError : Event
    }

    operator fun plus(event: Event) = copy(eventList = eventList + event)
    operator fun minus(events: List<Event>) = copy(eventList = eventList - events.toSet())
}
