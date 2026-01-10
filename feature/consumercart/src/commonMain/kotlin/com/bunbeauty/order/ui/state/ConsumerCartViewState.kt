package com.bunbeauty.order.ui.state

import androidx.compose.runtime.Immutable
import com.bunbeauty.core.base.BaseViewState
import com.bunbeauty.core.motivation.MotivationUi
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
        val recommendationList: ImmutableList<ProductUi>,
        val bottomPanelInfo: BottomPanelInfoUi?,
    ) : ConsumerCartViewState(state = State.SUCCESS) {
        data class ProductUi(
            val key: String,
            val uuid: String,
            val photoLink: String,
            val name: String,
            val oldPrice: String?,
            val newPrice: String,
        )
    }

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
