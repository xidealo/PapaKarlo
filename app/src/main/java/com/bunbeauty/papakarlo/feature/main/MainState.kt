package com.bunbeauty.papakarlo.feature.main

import androidx.compose.runtime.Immutable
import androidx.navigation.NavController
import com.bunbeauty.papakarlo.common.ui.element.bottombar.NavigationBarItem

data class MainState(
    val connectionLost: Boolean = false,
    val statusBarMessage: StatusBarMessage = StatusBarMessage(
        isVisible = false,
        workInfoType = null
    ),
    val navigationBarOptions: NavigationBarOptions = NavigationBarOptions.Hidden,
    val eventList: List<Event> = emptyList()
) {

    @Immutable
    data class StatusBarMessage(
        val isVisible: Boolean,
        val workInfoType: WorkType?
    ) {
        enum class WorkType {
            DELIVERY,
            PICKUP,
            CLOSED
        }
    }

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
    val text: String
)

sealed interface NavigationBarOptions {
    data object Hidden : NavigationBarOptions
    data class Visible(
        val selectedItem: NavigationBarItem,
        val navController: NavController
    ) : NavigationBarOptions
}
