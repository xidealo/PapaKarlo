package com.bunbeauty.shared.presentation.profile

import com.bunbeauty.shared.domain.model.link.Link
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethod
import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseDataState
import com.bunbeauty.shared.presentation.base.BaseEvent
import kotlinx.collections.immutable.ImmutableList

interface ProfileState {
    data class DataState(
        val lastOrder: LightOrder? = null,
        val state: State,
        val paymentMethodList: ImmutableList<PaymentMethod>,
        val linkList: List<Link>,
        val isUnauthorized: Boolean
    ) : BaseDataState {
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
        data class onLastOrderClicked(val uuid: String, val code: String) : Action
        data object onLoginClicked : Action
        data object onCafeListClicked : Action
        data class onPaymentClicked(val paymentMethodList: List<PaymentMethod>) : Action
        data class onFeedbackClicked(val linkList: List<Link>) : Action
        data object onAboutAppClicked : Action
    }

    sealed interface Event : BaseEvent {
        class OpenOrderDetails(val orderUuid: String, val orderCode: String) : Event
        data object OpenSettings : Event
        data object OpenAddressList : Event
        data object OpenOrderList : Event
        data object ShowCafeList : Event
        class ShowPayment(val paymentMethodList: List<PaymentMethod>) : Event
        class ShowFeedback(val linkList: List<Link>) : Event
        data object ShowAboutApp : Event
        data object OpenLogin : Event
        data object GoBackEvent : Event
    }
}

