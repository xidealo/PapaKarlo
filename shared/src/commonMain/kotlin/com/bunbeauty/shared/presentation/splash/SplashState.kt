package com.bunbeauty.shared.presentation.splash

import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseDataState
import com.bunbeauty.shared.presentation.base.BaseEvent

interface Splash {

    data object DataState : BaseDataState

    sealed interface Action : BaseAction {
        data class Init(val version: Int) : Action
    }

    sealed interface Event : BaseEvent {
        data object NavigateToUpdateEvent : Event
        data object NavigateToMenuEvent : Event
        data object NavigateToSelectCityEvent : Event
    }
}
