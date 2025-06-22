package com.bunbeauty.shared.presentation.profile

import com.bunbeauty.shared.domain.model.link.Link
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethod

data class ProfileState(
    val lastOrder: LightOrder? = null,
    val state: State = State.LOADING,
    val paymentMethodList: List<PaymentMethod> = emptyList(),
    val linkList: List<Link> = emptyList(),
    val eventList: List<Event> = emptyList()
) {

    enum class State {
        AUTHORIZED,
        UNAUTHORIZED,
        ERROR,
        LOADING
    }

    sealed interface Event {
        class OpenOrderDetails(val orderUuid: String, val orderCode: String) : Event
        data object OpenSettings : Event
        data object OpenAddressList : Event
        data object OpenOrderList : Event
        data object ShowCafeList : Event
        class ShowPayment(val paymentMethodList: List<PaymentMethod>) : Event
        class ShowFeedback(val linkList: List<Link>) : Event
        data object ShowAboutApp : Event
        data object OpenLogin : Event
    }

    operator fun plus(event: Event) = copy(eventList = eventList + event)
    operator fun minus(events: List<Event>) = copy(eventList = eventList - events.toSet())
}
