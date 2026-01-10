package com.bunbeauty.splash.presentation

import com.bunbeauty.core.base.BaseAction
import com.bunbeauty.core.base.BaseEvent
import com.bunbeauty.core.base.BaseViewDataState

interface Splash {
    data object DataState : BaseViewDataState

    sealed interface Action : BaseAction {
        data object Init : Action
    }

    sealed interface Effect : BaseEvent {
        data object NavigateToUpdateEffect : Effect

        data object NavigateToMenuEffect : Effect

        data object NavigateToSelectCityEffect : Effect
    }
}
