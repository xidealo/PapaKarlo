package com.bunbeauty.shared.presentation.order_details

import com.bunbeauty.shared.domain.model.addition.OrderAddition
import com.bunbeauty.shared.domain.model.date_time.DateTime
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.model.order.OrderAddress
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethodName
import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseDataState
import com.bunbeauty.shared.presentation.base.BaseEvent
import com.bunbeauty.shared.presentation.base.BaseViewDataState

interface OrderDetails {
    data class ViewDataState(
        val orderDetailsData: OrderDetailsData,
        val screenState: ScreenState,
    ) : BaseViewDataState {

        data class OrderDetailsData(
            val orderProductItemList: List<OrderProductItem>,
            val orderInfo: OrderInfo?,
            val oldTotalCost: String?,
            val deliveryCost: String?,
            val newTotalCost: String?,
            val discount: String?,
        ) : BaseDataState {
            data class OrderInfo(
                val code: String,
                val status: OrderStatus,
                val dateTime: DateTime,
                val deferredTime: Time?,
                val address: OrderAddress,
                val comment: String?,
                val isDelivery: Boolean,
                val paymentMethod: PaymentMethodName?,
            )

            data class OrderProductItem(
                val uuid: String,
                val name: String,
                val newPrice: String,
                val oldPrice: String?,
                val newCost: String,
                val oldCost: String?,
                val photoLink: String,
                val count: String,
                val additions: List<OrderAddition>,
            )
        }

        enum class ScreenState {
            LOADING,
            SUCCESS,
            ERROR
        }
    }

    sealed interface Action : BaseAction {
        data object Back : Action
        data class Init(val orderUuid: String) : Action
        object StopObserve : Action
    }

    sealed interface Event : BaseEvent {
        data object Back : Event
    }
}