package com.bunbeauty.papakarlo.feature.profile.screen.profile

import androidx.compose.runtime.Immutable
import com.bunbeauty.shared.presentation.base.BaseViewState

@Immutable
sealed class ProfileViewState(
    val state: State,
    val isUnauthorized: Boolean
) : BaseViewState {

    sealed interface State {
        data object Loading : State
        data object Error : State
        data object Success : State
    }
}

