package com.bunbeauty.papakarlo.feature.cafe.screen.cafelist

import androidx.compose.runtime.Immutable
import com.bunbeauty.papakarlo.feature.cafe.ui.CafeItemAndroid
import com.bunbeauty.papakarlo.feature.topcart.TopCartUi
import com.bunbeauty.shared.presentation.base.BaseViewState
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class CafeListViewState(
    val cafeList: ImmutableList<CafeItemAndroid>,
    val topCartUi: TopCartUi,
    val state: State
) : BaseViewState {
    sealed interface State {
        data object Success : State
        data object Loading : State
        data class Error(val throwable: Throwable) : State
    }
}
