package com.bunbeauty.papakarlo.feature.profile.screen.profile

import androidx.compose.runtime.Immutable
import com.bunbeauty.shared.presentation.base.BaseViewState

@Immutable
sealed class ProfileViewState(
    val state: State
): BaseViewState {

    enum class State {
        UNAUTHORIZED,
        LOADING,
        ERROR,
        SUCCESS
    }
}