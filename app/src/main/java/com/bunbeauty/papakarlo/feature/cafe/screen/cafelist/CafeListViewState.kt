package com.bunbeauty.papakarlo.feature.cafe.screen.cafelist

import androidx.compose.runtime.Immutable
import com.bunbeauty.papakarlo.feature.cafe.ui.CafeItemAndroid
import com.bunbeauty.shared.presentation.base.BaseViewState
import kotlinx.collections.immutable.ImmutableList

@Immutable
sealed class CafeListViewState(
    val state: State
) : BaseViewState {

    @Immutable
    data class Success(
        val cafeList: ImmutableList<CafeItemAndroid>
    ) : CafeListViewState(state = State.SUCCESS)

    @Immutable
    data object Loading : CafeListViewState(state = State.LOADING)

    @Immutable
    data class Error(val throwable: Throwable) : CafeListViewState(state = State.ERROR)

    enum class State {
        LOADING,
        ERROR,
        SUCCESS
    }
}
