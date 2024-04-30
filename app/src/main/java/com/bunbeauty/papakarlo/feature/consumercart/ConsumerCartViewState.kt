package com.bunbeauty.papakarlo.feature.consumercart

import androidx.compose.runtime.Immutable
import com.bunbeauty.papakarlo.feature.menu.model.MenuItemUi
import com.bunbeauty.shared.presentation.base.BaseViewState
import com.bunbeauty.shared.presentation.consumercart.CartProductItem
import kotlinx.collections.immutable.ImmutableList

@Immutable
sealed class ConsumerCartViewState(
    val state: State
) : BaseViewState {

    @Immutable
    data object Loading : ConsumerCartViewState(state = State.LOADING)

    @Immutable
    data class Success(
        val warning: WarningUi?,
        val cartProductList: ImmutableList<CartProductItem>,
        val recommendationList: ImmutableList<MenuItemUi.Product>,
        val discount: String?,
        val oldTotalCost: String?,
        val newTotalCost: String,
    ) : ConsumerCartViewState(state = State.SUCCESS)

    @Immutable
    data class Empty(
        val recommendationList: ImmutableList<MenuItemUi.Product>
    ) : ConsumerCartViewState(state = State.EMPTY)

    @Immutable
    data object Error : ConsumerCartViewState(state = State.ERROR)

    @Immutable
    sealed interface WarningUi {
        @Immutable
        data class MinOrderCost(val cost: String) : WarningUi

        @Immutable
        data class ForFreeDelivery(val increaseAmountBy: String) : WarningUi

        @Immutable
        data class ForLowerDelivery(val increaseAmountBy: String) : WarningUi
    }

    enum class State {
        LOADING,
        ERROR,
        EMPTY,
        SUCCESS,
    }

}