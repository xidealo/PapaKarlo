package com.bunbeauty.papakarlo.feature.main

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class MainState(
    val connectionLost: Boolean = false,
    val statusBarMessage: StatusBarMessage =
        StatusBarMessage(
            isVisible = false,
        ),
    val paddingBottomSnackbar: Int = 0,
    val statusBarColor: Color? = null,
    val eventList: List<Event> = emptyList(),
) {
    @Immutable
    data class StatusBarMessage(
        val isVisible: Boolean,
    )

    sealed interface Event {
        class ShowMessageEvent(
            val message: FoodDeliveryMessage,
        ) : Event
    }

    operator fun plus(event: Event) = copy(eventList = eventList + event)

    operator fun minus(events: List<Event>) = copy(eventList = eventList - events.toSet())
}

enum class FoodDeliveryMessageType {
    INFO,
    ERROR,
}

data class FoodDeliveryMessage(
    val type: FoodDeliveryMessageType,
    val text: String,
)
