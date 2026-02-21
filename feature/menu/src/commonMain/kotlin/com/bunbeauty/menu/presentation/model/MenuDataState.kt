package com.bunbeauty.menu.presentation.model

import com.bunbeauty.core.model.CategoryItem
import com.bunbeauty.core.model.MenuItem
import com.bunbeauty.core.model.cart.CartCostAndCount

data class MenuDataState(
    val categoryItemList: List<CategoryItem>,
    val cartCostAndCount: CartCostAndCount?,
    val menuItemList: List<MenuItem>,
    val state: State,
    val userScrollEnabled: Boolean,
    val eventList: List<Event>,
) {
    sealed class State {
        data object Success : State()

        data object Loading : State()

        data class Error(
            val throwable: Throwable,
        ) : State()
    }

    sealed interface Event {
        data class GoToSelectedItem(
            val uuid: String,
            val name: String,
        ) : Event

        data object ShowAddProductError : Event

        data class ShowAddedProduct(
            val name: String,
        ) : Event
    }

    operator fun plus(event: Event) = copy(eventList = eventList + event)

    operator fun minus(events: List<Event>) = copy(eventList = eventList - events.toSet())

    val hasDiscountItem = menuItemList.any { it is MenuItem.Discount }
}
