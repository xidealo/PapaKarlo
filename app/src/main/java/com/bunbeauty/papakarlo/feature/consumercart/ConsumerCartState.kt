package com.bunbeauty.papakarlo.feature.consumercart

import com.bunbeauty.papakarlo.common.model.SuccessLoginDirection
import com.bunbeauty.papakarlo.feature.consumercart.model.CartProductItem

data class ConsumerCartDataState(
    val consumerCartData: ConsumerCartData? = null,
    val state: State = State.LOADING,
    val eventList: List<ConsumerCartEvent> = emptyList(),
) {

    enum class State {
        LOADING,
        SUCCESS,
        EMPTY,
        ERROR
    }

    operator fun plus(event: ConsumerCartEvent) = copy(eventList = eventList + event)
    operator fun minus(events: List<ConsumerCartEvent>) =
        copy(eventList = eventList - events.toSet())
}

data class ConsumerCartData(
    val forFreeDelivery: String,
    val cartProductList: List<CartProductItem>,
    val oldTotalCost: String?,
    val newTotalCost: String,
    val firstOrderDiscount: String?,
)

sealed interface ConsumerCartEvent {
    data object NavigateToMenuEvent : ConsumerCartEvent
    data object NavigateToCreateOrderEvent : ConsumerCartEvent
    class NavigateToLoginEvent(val successLoginDirection: SuccessLoginDirection) : ConsumerCartEvent
    class NavigateToProductEvent(val cartProductItem: CartProductItem) : ConsumerCartEvent
}

data class ConsumerCartUIState(
    val consumerCartState: ConsumerCartState = ConsumerCartState.Loading,
    val eventList: List<ConsumerCartEvent> = emptyList(),
) {

    sealed interface ConsumerCartState {
        data object Loading : ConsumerCartState
        data class Success(val data: ConsumerCartData) : ConsumerCartState

        data object Empty : ConsumerCartState
        data object Error : ConsumerCartState
    }
}
