package com.bunbeauty.shared.presentation.product_details

import com.bunbeauty.core.model.ProductDetailsOpenedFrom
import com.bunbeauty.core.model.addition.AdditionGroup
import com.bunbeauty.core.model.cart.CartCostAndCount
import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseDataState
import com.bunbeauty.shared.presentation.base.BaseEvent

interface ProductDetailsState {
    data class DataState(
        val cartCostAndCount: CartCostAndCount?,
        val menuProduct: MenuProduct,
        val screenState: ScreenState,
    ) : BaseDataState {
        data class MenuProduct(
            val uuid: String,
            val photoLink: String,
            val name: String,
            val size: String,
            val oldPrice: Int?,
            val newPrice: Int,
            val currency: String,
            val priceWithAdditions: Int,
            val description: String,
            val additionGroups: List<AdditionGroup>,
        ) {
            val additionList =
                additionGroups.flatMap { additionGroups -> additionGroups.additionList }
        }

        enum class ScreenState {
            SUCCESS,
            ERROR,
            LOADING,
            INIT,
        }
    }

    sealed interface Action : BaseAction {
        data class Init(
            val menuProductUuid: String,
            val selectedAdditionUuidList: List<String>,
        ) : Action

        data object BackClick : Action

        data class AdditionClick(
            val uuid: String,
            val groupUuid: String,
        ) : Action

        data class AddProductToCartClick(
            val productDetailsOpenedFrom: ProductDetailsOpenedFrom,
            val cartProductUuid: String?,
        ) : Action
    }

    sealed interface Event : BaseEvent {
        data object NavigateBack : Event

        data object AddedProduct : Event

        data object EditedProduct : Event

        data object ShowAddProductError : Event
    }
}
