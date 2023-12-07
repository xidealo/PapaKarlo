package com.bunbeauty.shared.presentation.product_details

import com.bunbeauty.shared.domain.model.cart.CartCostAndCount
import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseEvent
import com.bunbeauty.shared.presentation.base.BaseViewDataState

interface ProductDetailsState {

    data class ViewDataState(
        val cartCostAndCount: CartCostAndCount?,
        val menuProduct: MenuProduct?,
        val screenState: ScreenState,
    ) : BaseViewDataState {
        data class MenuProduct(
            val uuid: String,
            val photoLink: String,
            val name: String,
            val size: String,
            val oldPrice: String?,
            val newPrice: String,
            val priceWithAdditions: String,
            val description: String,
            val additionList: List<AdditionItem>,
        )

        enum class ScreenState {
            SUCCESS,
            ERROR,
            LOADING,
        }
    }

    sealed interface Action : BaseAction {
        data class Init(val menuProductUuid: String) : Action
        data object BackClick : Action
        data object CartClick : Action
        data class AdditionClick(val uuid: String, val groupId: String) : Action
        data class AddProductToCartClick(
            val productDetailsOpenedFrom: ProductDetailsOpenedFrom,
        ) : Action
    }

    sealed interface Event : BaseEvent {
        data object NavigateBack : Event
        data object NavigateToConsumerCart : Event
    }
}

