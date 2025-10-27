package com.bunbeauty.papakarlo.feature.consumercart.state

import androidx.compose.runtime.Immutable
import com.bunbeauty.papakarlo.feature.menu.state.MenuItemUi
import com.bunbeauty.papakarlo.feature.motivation.MotivationUi
import com.bunbeauty.shared.presentation.base.BaseViewState
import kotlinx.collections.immutable.ImmutableList

@Immutable
sealed class ConsumerCartViewState(
    val state: State,
) : BaseViewState {
    @Immutable
    data object Loading : ConsumerCartViewState(state = State.LOADING)

    @Immutable
    data class Success(
        val cartProductList: ImmutableList<CartProductItemUi>,
        val recommendationList: ImmutableList<MenuItemUi.Product>,
        val bottomPanelInfo: BottomPanelInfoUi?,
    ) : ConsumerCartViewState(state = State.SUCCESS)

    @Immutable
    data object Error : ConsumerCartViewState(state = State.ERROR)

    @Immutable
    data class BottomPanelInfoUi(
        val motivation: MotivationUi?,
        val discount: String?,
        val oldTotalCost: String?,
        val newTotalCost: String,
        val orderAvailable: Boolean,
    )

    @Immutable
    data class CartProductItemUi(
        val key: String,
        val uuid: String,
        val name: String,
        val newCost: String,
        val oldCost: String?,
        val photoLink: String,
        val count: Int,
        val additions: String?,
        val isLast: Boolean,
    )

    enum class State {
        LOADING,
        ERROR,
        SUCCESS,
    }
}
