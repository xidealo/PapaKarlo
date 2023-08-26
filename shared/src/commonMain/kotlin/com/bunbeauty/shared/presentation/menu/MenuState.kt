package com.bunbeauty.shared.presentation.menu

import com.bunbeauty.shared.domain.model.cart.CartCostAndCount

data class MenuState(
    val categoryItemList: List<CategoryItem>,
    val cartCostAndCount: CartCostAndCount?,
    val menuItemList: List<MenuItem>,
    val discount: String?,
    val state: State,
    val eventList: List<Event>,
) {
    sealed class State {
        data object Success : State()
        data object Loading : State()
        data class Error(val throwable: Throwable) : State()
    }

    sealed interface Event {
        data class GoToSelectedItem(val uuid: String, val name: String) : Event
    }

    operator fun plus(event: Event) = copy(eventList = eventList + event)
    operator fun minus(events: List<Event>) = copy(eventList = eventList - events.toSet())
}
