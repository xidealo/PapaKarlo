package com.bunbeauty.papakarlo.feature.main

import androidx.navigation.NavController
import com.bunbeauty.papakarlo.common.ui.element.bottombar.NavigationBarItem

data class MainState(
    val connectionLost: Boolean = false,
    val navigationBarOptions: NavigationBarOptions = NavigationBarOptions.Hidden,
    val eventList: List<Event> = emptyList()
) {

    sealed interface Event {
        class ShowMessageEvent(val message: FoodDeliveryMessage) : Event
    }

    operator fun plus(event: Event) = copy(eventList = eventList + event)
    operator fun minus(events: List<Event>) = copy(eventList = eventList - events.toSet())
}

enum class FoodDeliveryMessageType {
    INFO,
    ERROR
}

data class FoodDeliveryMessage(
    val type: FoodDeliveryMessageType,
    val text: String,
)

sealed interface NavigationBarOptions {
    object Hidden : NavigationBarOptions
    data class Visible(
        val selectedItem: NavigationBarItem,
        val navController: NavController
    ) : NavigationBarOptions
}
