package com.bunbeauty.shared.presentation.createorder

import com.bunbeauty.shared.domain.model.address.SelectableUserAddress
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.model.cafe.SelectableCafe
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethod
import com.bunbeauty.shared.domain.model.payment_method.SelectablePaymentMethod
import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseDataState
import com.bunbeauty.shared.presentation.base.BaseEvent

interface CreateOrder {

    data class DataState(
        val isDelivery: Boolean = true,

        val userAddressList: List<SelectableUserAddress> = emptyList(),
        val selectedUserAddress: UserAddress? = null,
        val showUserAddressList: Boolean = false,
        val isUserAddressError: Boolean = false,

        val cafeList: List<SelectableCafe> = emptyList(),
        val showCafeList: Boolean = false,
        val selectedCafe: Cafe? = null,

        val comment: String? = null,
        val deferredTime: DeferredTime = DeferredTime.Asap,

        val paymentMethodList: List<SelectablePaymentMethod> = emptyList(),
        val selectedPaymentMethod: PaymentMethod? = null,
        val isPaymentMethodError: Boolean = false,

        val cartTotal: CartTotal,

        val isLoading: Boolean,
    ) : BaseDataState

    sealed interface DeferredTime {
        data object Asap : DeferredTime
        data class Later(val time: Time) : DeferredTime
    }

    sealed interface CartTotal {
        data object Loading : CartTotal
        data class Success(
            val discount: String?,
            val deliveryCost: String?,
            val oldFinalCost: String?,
            val newFinalCost: String,
        ) : CartTotal
    }

    sealed interface Action : BaseAction {
        data object Update : Action
        data class ChangeMethod(val position: Int) : Action
        data object DeliveryAddressClick : Action
        data object HideDeliveryAddressList : Action
        data class ChangeDeliveryAddress(val addressUuid: String) : Action
        data object PickupAddressClick : Action
        data object HidePickupAddressList : Action
        data class ChangePickupAddress(val addressUuid: String) : Action
        data object PaymentMethodClick : Action
        data class ChangePaymentMethod(val paymentMethodUuid: String) : Action
        data object DeferredTimeClick : Action
        data class ChangeDeferredTime(val deferredTime: Time?): Action
        data object CommentClick : Action
        data class ChangeComment(val comment: String): Action
        data object CreateClick : Action
    }

    sealed interface Event : BaseEvent {
        data object OpenCreateAddressEvent : Event

        data class ShowDeferredTimeEvent(
            val deferredTime: DeferredTime,
            val minTime: Time,
            val isDelivery: Boolean,
        ) : Event

        data class ShowCommentInputEvent(val comment: String?) : Event
        data object ShowUserUnauthorizedErrorEvent : Event
        data object ShowSomethingWentWrongErrorEvent : Event
        data class OrderCreatedEvent(val code: String) : Event
        data object ShowUserAddressError : Event
        data object ShowPaymentMethodError : Event
        data class ShowPaymentMethodList(val selectablePaymentMethodList: List<SelectablePaymentMethod>) : Event
    }

}