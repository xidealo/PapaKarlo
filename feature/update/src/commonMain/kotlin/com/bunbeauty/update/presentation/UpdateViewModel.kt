package com.bunbeauty.update.presentation

import com.bunbeauty.core.model.link.LinkType
import com.bunbeauty.core.extension.launchSafe
import com.bunbeauty.core.base.SharedStateViewModel
import com.bunbeauty.core.domain.link.GetLinkUseCase

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
            is UpdateState.Action.Init -> updateLink(linkType = action.linkType)

            is UpdateState.Action.UpdateClick -> {
                addEvent {
                    UpdateState.Event.NavigateToUpdateEvent(linkValue = action.linkValue)
                }
            }
        }
    }

    private fun updateLink(linkType: LinkType) {
        sharedScope.launchSafe(
            block = {
                val link = getLinkUseCase(linkType = linkType)
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
