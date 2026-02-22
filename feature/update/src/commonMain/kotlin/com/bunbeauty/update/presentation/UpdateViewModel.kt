package com.bunbeauty.update.presentation

import com.bunbeauty.core.base.SharedStateViewModel
import com.bunbeauty.core.domain.link.GetLinkUseCase
import com.bunbeauty.core.extension.launchSafe

class UpdateViewModel(
    private val getLinkUseCase: GetLinkUseCase,
) : SharedStateViewModel<UpdateState.DataState, UpdateState.Action, UpdateState.Event>(
        initDataState =
            UpdateState.DataState(
                link = null,
                state = UpdateState.DataState.State.LOADING,
            ),
    ) {
    override fun reduce(
        action: UpdateState.Action,
        dataState: UpdateState.DataState,
    ) {
        when (action) {
            is UpdateState.Action.Init -> updateLink()

            is UpdateState.Action.UpdateClick -> {
                addEvent {
                    UpdateState.Event.NavigateToUpdateEvent(linkValue = action.linkValue)
                }
            }
        }
    }

    private fun updateLink() {
        sharedScope.launchSafe(
            block = {
                val link = getLinkUseCase()
                setState {
                    copy(
                        state =
                            if (link == null) {
                                UpdateState.DataState.State.ERROR
                            } else {
                                UpdateState.DataState.State.SUCCESS
                            },
                        link = link,
                    )
                }
            },
            onError = {
                setState {
                    copy(
                        state = UpdateState.DataState.State.ERROR,
                    )
                }
            },
        )
    }
}
