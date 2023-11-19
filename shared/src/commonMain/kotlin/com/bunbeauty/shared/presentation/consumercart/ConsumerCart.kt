package com.bunbeauty.shared.presentation.consumercart

import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseEvent
import com.bunbeauty.shared.presentation.base.BaseViewDataState
import com.bunbeauty.shared.presentation.menu.MenuProductItem
import com.bunbeauty.shared.presentation.product_details.ProductDetailsOpenedFrom

interface ConsumerCart {

    data class ViewDataState(
        val consumerCartData: ConsumerCartData?,
        val screenState: ScreenState,
    ) : BaseViewDataState {

        fun getIsLastProduct(menuProductUuid: String): Boolean {
            return consumerCartData?.cartProductList?.find { it.menuProductUuid == menuProductUuid }?.count == 1
        }

        data class ConsumerCartData(
            val forFreeDelivery: String,
            val cartProductList: List<CartProductItem>,
            val oldTotalCost: String?,
            val newTotalCost: String,
            val firstOrderDiscount: String?,
            val recommendations: List<MenuProductItem>,
        )

        enum class ScreenState {
            LOADING,
            SUCCESS,
            EMPTY,
            ERROR
        }
    }

    sealed interface Action : BaseAction {
        data object Init : Action
        data object BackClick : Action
        data class AddProductToCartClick(val menuProductUuid: String) : Action
        data class RemoveProductFromCartClick(val menuProductUuid: String) : Action
        data class OnProductClick(val cartProductItem: CartProductItem) : Action
        data object OnCreateOrderClick : Action
        data object OnMenuClick : Action
        data object OnErrorButtonClick : Action
        data class AddRecommendationProductToCartClick(val menuProductUuid: String) : Action
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
        ) : Event

        data object NavigateBack : Event
    }
}

