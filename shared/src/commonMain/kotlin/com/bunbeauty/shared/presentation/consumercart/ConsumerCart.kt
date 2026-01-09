package com.bunbeauty.shared.presentation.consumercart

import com.bunbeauty.core.model.MenuItem
import com.bunbeauty.core.base.BaseAction
import com.bunbeauty.core.base.BaseDataState
import com.bunbeauty.core.base.BaseEvent
import com.bunbeauty.shared.presentation.motivation.MotivationData
import com.bunbeauty.core.model.ProductDetailsOpenedFrom

interface ConsumerCart {
    data class DataState(
        val state: State,
        val motivation: MotivationData?,
        val cartProductItemList: List<CartProductItem>,
        val recommendationList: List<MenuItem.Product>,
        val discount: String?,
        val oldTotalCost: String?,
        val newTotalCost: String,
        val orderAvailable: Boolean,
    ) : BaseDataState {
        enum class State {
            LOADING,
            SUCCESS,
            ERROR,
        }
    }

    sealed interface Action : BaseAction {
        data object Init : Action

        data object BackClick : Action

        data class AddProductToCartClick(
            val cartProductUuid: String,
        ) : Action

        data class RemoveProductFromCartClick(
            val cartProductUuid: String,
        ) : Action

        data class OnCartProductClick(
            val cartProductUuid: String,
        ) : Action

        data object OnCreateOrderClick : Action

        data object OnMenuClick : Action

        data object OnErrorButtonClick : Action

        data class AddRecommendationProductToCartClick(
            val menuProductUuid: String,
        ) : Action

        data class RecommendationClick(
            val menuProductUuid: String,
        ) : Action
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
