package com.bunbeauty.papakarlo.feature.consumercart

import com.bunbeauty.papakarlo.feature.menu.model.MenuItemUi
import com.bunbeauty.shared.presentation.base.BaseViewState
import com.bunbeauty.shared.presentation.consumercart.CartProductItem
import kotlinx.collections.immutable.ImmutableList

sealed class ConsumerCartViewState(
    val state: State
) : BaseViewState {

    data object Loading : ConsumerCartViewState(state = State.LOADING)

    data class Success(
        val warning: Warning?,
        val cartProductList: ImmutableList<CartProductItem>,
        val recommendationList: ImmutableList<MenuItemUi.Product>,
        val discount: String?,
        val oldTotalCost: String?,
        val newTotalCost: String,
    ) : ConsumerCartViewState(state = State.SUCCESS)

    data class Empty(
        val recommendationList: ImmutableList<MenuItemUi.Product>
    ) : ConsumerCartViewState(state = State.EMPTY)

    data object Error : ConsumerCartViewState(state = State.ERROR)


    sealed interface Warning {
        data class MinOrderCost(val cost: String) : Warning
        data class ForFreeDelivery(val increaseAmountBy: String) : Warning
        data class ForLowerDelivery(val increaseAmountBy: String) : Warning
    }

    enum class State {
        LOADING,
        ERROR,
        EMPTY,
        SUCCESS,
    }

}