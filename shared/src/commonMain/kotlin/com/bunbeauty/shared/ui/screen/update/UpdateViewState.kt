package com.bunbeauty.shared.ui.screen.update

import androidx.compose.runtime.Immutable
import com.bunbeauty.core.model.link.Link
import com.bunbeauty.shared.presentation.base.BaseViewState

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
