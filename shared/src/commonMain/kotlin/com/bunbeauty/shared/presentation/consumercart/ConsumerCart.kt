package com.bunbeauty.shared.presentation.consumercart

import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseEvent
import com.bunbeauty.shared.presentation.base.BaseViewDataState
import com.bunbeauty.shared.presentation.menu.model.MenuItem
import com.bunbeauty.shared.presentation.product_details.ProductDetailsOpenedFrom

interface ConsumerCart {

    data class DataState(
        val state: State,
        val warningItem: WarningItem?,
        val cartProductItemList: List<CartProductItem>,
        val recommendationList: List<MenuItem.Product>,
        val oldTotalCost: String?,
        val newTotalCost: String,
        val discount: String?,
    ) : BaseViewDataState {

        fun getIsLastProduct(menuProductUuid: String): Boolean {
            return cartProductItemList.find { cartProductItem ->
                cartProductItem.menuProductUuid == menuProductUuid
            }?.count == 1
        }

        sealed interface WarningItem {
            data class MinOrderCost(val cost: String) : WarningItem
            data class ForFreeDelivery(val increaseAmountBy: String) : WarningItem
            data class ForLowerDelivery(val increaseAmountBy: String) : WarningItem
        }

        enum class State {
            LOADING,
            SUCCESS,
            EMPTY,
            ERROR
        }
    }

    sealed interface Action : BaseAction {
        data object Init : Action
        data object BackClick : Action
        data class AddProductToCartClick(
            val cartProductUuid: String,
            val menuProductUuid: String,
        ) : Action

        data class RemoveProductFromCartClick(
            val menuProductUuid: String,
            val cartProductUuid: String,
        ) : Action

        data class OnProductClick(val cartProductItem: CartProductItem) : Action
        data object OnCreateOrderClick : Action
        data object OnMenuClick : Action
        data object OnErrorButtonClick : Action
        data class AddRecommendationProductToCartClick(
            val menuProductUuid: String,
        ) : Action

        data class RecommendationClick(val menuProductUuid: String, val name: String) : Action
    }

    sealed interface Event : BaseEvent {
        data object NavigateToMenu : Event
        data object NavigateToCreateOrder : Event
        data object NavigateToLogin : Event
        data class NavigateToProduct(
            val uuid: String,
            val name: String,
            val productDetailsOpenedFrom: ProductDetailsOpenedFrom,
            val additionUuidList: List<String>,
            val cartProductUuid: String?,
        ) : Event

        data object NavigateBack : Event
        data object ShowAddProductError : Event
        data object ShowRemoveProductError : Event
    }
}

