package com.bunbeauty.papakarlo.feature.create_order.screen.create_order

import androidx.annotation.StringRes
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.feature.create_order.model.TimeUI
import com.bunbeauty.papakarlo.feature.create_order.screen.user_address_list.UserAddressItem
import com.bunbeauty.papakarlo.feature.edit_text.model.EditTextSettings

data class OrderCreationUiState(
    val isDelivery: Boolean = true,
    @StringRes val addressLabelId: Int = R.string.delivery_address,
    val deliveryAddress: String? = null,
    val pickupAddress: String? = null,
    val comment: String? = null,
    @StringRes val deferredTimeLabelId: Int = R.string.delivery_time,
    val deferredTime: String? = null,
    val totalCost: String? = null,
    val deliveryCost: String? = null,
    val finalCost: String? = null,
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
        class OpenUserAddressListEvent(val addressList: List<UserAddressItem>) : Event
        object ShowCafeAddressListEvent : Event
        class ShowDeferredTimeEvent(val title: String, val time: TimeUI?) : Event
        class ShowCommentInputEvent(val inputSettings: EditTextSettings) : Event
        class ShowAddressErrorEvent(val message: String) : Event
        class ShowErrorEvent(val message: String) : Event
        class OrderCreatedEvent(val code: String) : Event
    }

    operator fun plus(event: Event) = copy(eventList = eventList + event)
    operator fun minus(events: List<Event>) = copy(eventList = eventList - events.toSet())
}