package com.bunbeauty.papakarlo.feature.create_order.screen.create_order

import com.bunbeauty.papakarlo.feature.create_order.model.TimeUI
import com.bunbeauty.papakarlo.feature.create_order.screen.cafe_address_list.CafeAddressItem
import com.bunbeauty.papakarlo.feature.create_order.screen.user_address_list.UserAddressItem

data class OrderCreationUiState(
    val isDelivery: Boolean = true,
    val deliveryAddress: String? = null,
    val pickupAddress: String? = null,
    val comment: String? = null,
    val deferredTime: String = "",
    val totalCost: String? = null,
    val deliveryCost: String? = null,
    val finalCost: String? = null,
    val isAddressErrorShown: Boolean = false,
    val isLoading: Boolean = false,
    val eventList: List<Event> = emptyList(),
) {
    val switcherPosition
        get() = if (isDelivery) {
            0
        } else {
            1
        }

    sealed interface Event {
        object OpenCreateAddressEvent : Event
        class ShowUserAddressListEvent(val addressList: List<UserAddressItem>) : Event
        class ShowCafeAddressListEvent(val addressList: List<CafeAddressItem>) : Event
        class ShowDeferredTimeEvent(
            val deferredTime: TimeUI,
            val minTime: TimeUI.Time,
            val isDelivery: Boolean
        ) : Event

        class ShowCommentInputEvent(val comment: String?) : Event
        object ShowUserUnauthorizedErrorEvent : Event
        object ShowSomethingWentWrongErrorEvent : Event
        class OrderCreatedEvent(val code: String) : Event
    }

    operator fun plus(event: Event) = copy(eventList = eventList + event)
    operator fun minus(events: List<Event>) = copy(eventList = eventList - events.toSet())
}
