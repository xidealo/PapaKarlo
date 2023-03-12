package com.bunbeauty.papakarlo.feature.cafe.screen.cafe_list

import com.bunbeauty.papakarlo.feature.cafe.model.CafeItem
import com.bunbeauty.shared.domain.model.cart.CartCostAndCount

data class CafeListState(
    //TODO(convert to CafeItem in CafeListUI)
    val cafeList: List<CafeItem> = emptyList(),
    val cartCostAndCount: CartCostAndCount? = null,
    val state: State = State.Loading,
    val eventList: List<Event> = emptyList(),
) {
    sealed class State {
        object Success : State()
        object Loading : State()
        data class Error(val throwable: Throwable) : State()
    }

    sealed interface Event {
        data class OpenCafeOptionsBottomSheet(val uuid: String) : Event
    }

    operator fun plus(event: Event) = copy(eventList = eventList + event)
    operator fun minus(events: List<Event>) = copy(eventList = eventList - events.toSet())
}