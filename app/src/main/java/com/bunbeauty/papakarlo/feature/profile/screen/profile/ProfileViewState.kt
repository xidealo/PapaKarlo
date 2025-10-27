package com.bunbeauty.papakarlo.feature.profile.screen.profile

import androidx.compose.runtime.Immutable
import com.bunbeauty.papakarlo.feature.profile.screen.feedback.model.LinkUI
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.presentation.base.BaseViewState
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
