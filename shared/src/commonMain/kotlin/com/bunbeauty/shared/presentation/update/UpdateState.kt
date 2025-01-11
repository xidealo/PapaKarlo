package com.bunbeauty.shared.presentation.update

import com.bunbeauty.shared.domain.model.link.Link
import com.bunbeauty.shared.domain.model.link.LinkType
import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseEvent
import com.bunbeauty.shared.presentation.base.BaseViewDataState

sealed interface UpdateState {
    data class DataState(
        val link: Link?,
        val state: State
    ) : BaseViewDataState {
        enum class State {
            LOADING,
            SUCCESS,
            ERROR
        }
    }

    sealed interface Action : BaseAction {
        data class Init(val linkType: LinkType) : Action
        data class UpdateClick(val linkValue: String) : Action
    }

    sealed interface Event : BaseEvent {
        data class NavigateToUpdateEvent(val linkValue: String) : Event
    }
}
