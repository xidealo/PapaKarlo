package com.bunbeauty.papakarlo.feature.splash

sealed interface SplashEvent {
    object NavigateToUpdateEvent : SplashEvent
    object NavigateToMenuEvent : SplashEvent
    object NavigateToSelectCityEvent : SplashEvent
}
