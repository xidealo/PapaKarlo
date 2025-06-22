package com.bunbeauty.shared.presentation.profile

import com.bunbeauty.shared.domain.model.link.Link
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethod
import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseDataState
import com.bunbeauty.shared.presentation.base.BaseEvent
import com.bunbeauty.shared.presentation.cafe_list.CafeList

interface ProfileState{
    data class DataState(
        val lastOrder: LightOrder? = null,
        val state: State = State.LOADING,
        val paymentMethodList: List<PaymentMethod> = emptyList(),
        val linkList: List<Link> = emptyList(),
        val eventList: List<Event> = emptyList()
    ): BaseDataState {

        enum class State {
            AUTHORIZED,
            UNAUTHORIZED,
            ERROR,
            LOADING
        }
    }

    sealed interface Action : BaseAction {
        data object Init : Action
        data object BackClicked : Action
        data object OnRefreshClicked : Action
        data object onYourAddressesClicked : Action
        data object onOrderHistoryClicked : Action
        data object onSettingsClick : Action
        data object onLastOrderClicked : Action
    }

    sealed interface Event: BaseEvent {
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

