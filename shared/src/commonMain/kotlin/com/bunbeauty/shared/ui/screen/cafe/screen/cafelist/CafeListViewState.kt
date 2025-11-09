package com.bunbeauty.shared.ui.screen.cafe.screen.cafelist

import androidx.compose.runtime.Immutable
import com.bunbeauty.shared.ui.screen.cafe.model.CafeOptions
import com.bunbeauty.shared.ui.screen.cafe.ui.CafeItemAndroid
import com.bunbeauty.shared.presentation.base.BaseViewState
import kotlinx.collections.immutable.ImmutableList

@Immutable
sealed class CafeListViewState(
    val state: State,
) : BaseViewState {
    @Immutable
    data class Success(
        val cafeList: ImmutableList<CafeItemAndroid>,
        val cafeOptionUI: CafeOptionUI,
    ) : CafeListViewState(state = State.SUCCESS)

    @Immutable
    data object Loading : CafeListViewState(state = State.LOADING)

    @Immutable
    data class Error(
        val throwable: Throwable,
    ) : CafeListViewState(state = State.ERROR)

    @Immutable
    data class CafeOptionUI(
        val isShown: Boolean,
        val cafeOptions: CafeOptions? = null,
    )

    enum class State {
        LOADING,
        ERROR,
        SUCCESS,
    }
}
