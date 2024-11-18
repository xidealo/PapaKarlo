package com.bunbeauty.shared.presentation.splash

import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseEvent
import com.bunbeauty.shared.presentation.base.BaseViewDataState

interface Splash {

    data object DataState : BaseViewDataState

    sealed interface Action : BaseAction {
        data object Init : Action
    }

    sealed interface Event : BaseEvent {
        data object NavigateToUpdateEvent : Event
        data object NavigateToMenuEvent : Event
        data object NavigateToSelectCityEvent : Event
    }
}
