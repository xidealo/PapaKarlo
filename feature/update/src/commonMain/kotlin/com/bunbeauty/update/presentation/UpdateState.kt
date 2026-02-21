package com.bunbeauty.update.presentation

import com.bunbeauty.core.base.BaseAction
import com.bunbeauty.core.base.BaseEvent
import com.bunbeauty.core.base.BaseViewDataState
import com.bunbeauty.core.model.link.Link

sealed interface UpdateState {
    data class DataState(
        val link: Link?,
        val state: State,
    ) : BaseViewDataState {
        enum class State {
            LOADING,
            SUCCESS,
            ERROR,
        }
    }

    sealed interface Action : BaseAction {
        data object Init : Action

        data class UpdateClick(
            val linkValue: String,
        ) : Action
    }

    sealed interface Event : BaseEvent {
        data class NavigateToUpdateEvent(
            val linkValue: String,
        ) : Event
    }
}
