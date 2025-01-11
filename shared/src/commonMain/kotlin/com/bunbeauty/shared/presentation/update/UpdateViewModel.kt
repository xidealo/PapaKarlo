package com.bunbeauty.shared.presentation.update

import com.bunbeauty.shared.domain.feature.link.GetLinkUseCase
import com.bunbeauty.shared.domain.model.link.LinkType
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.base.SharedStateViewModel

class UpdateViewModel(
    private val getLinkUseCase: GetLinkUseCase
) : SharedStateViewModel<UpdateState.DataState, UpdateState.Action, UpdateState.Event>(
    initDataState = UpdateState.DataState(
        link = null,
        state = UpdateState.DataState.State.LOADING
    )
) {

    override fun reduce(action: UpdateState.Action, dataState: UpdateState.DataState) {
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
                        state = if (link == null) {
                            UpdateState.DataState.State.ERROR
                        } else {
                            UpdateState.DataState.State.SUCCESS
                        },
                        link = link
                    )
                }
            },
            onError = {
                setState {
                    copy(
                        state = UpdateState.DataState.State.ERROR
                    )
                }
            }
        )
    }
}
