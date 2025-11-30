package com.bunbeauty.shared.ui.screen.profile.screen.profile

import androidx.compose.runtime.Immutable
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.presentation.base.BaseViewState
import com.bunbeauty.shared.ui.screen.profile.screen.feedback.model.LinkUI
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class ProfileViewState(
    val lastOrder: LightOrder? = null,
    val state: State,
    val aboutBottomSheetUI: AboutBottomSheetUI,
    val feedBackBottomSheetUI: FeedBackBottomSheetUI,
) : BaseViewState {
    @Immutable
    data class AboutBottomSheetUI(
        val isShown: Boolean,
        val version: String
    )

    @Immutable
    data class FeedBackBottomSheetUI(
        val isShown: Boolean,
        val linkList: ImmutableList<LinkUI>,
    )

    @Immutable
    sealed interface State {
        data object Loading : State

        data object Error : State

        data object Authorized : State

        data object Unauthorized : State
    }
}
