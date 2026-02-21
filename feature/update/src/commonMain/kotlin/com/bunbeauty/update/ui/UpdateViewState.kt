package com.bunbeauty.update.ui

import androidx.compose.runtime.Immutable
import com.bunbeauty.core.base.BaseViewState
import com.bunbeauty.core.model.link.Link

@Immutable
data class UpdateViewState(
    val state: State,
) : BaseViewState {
    @Immutable
    sealed interface State {
        data object Loading : State

        data object Error : State

        data class Success(
            val link: Link?,
        ) : State
    }
}
