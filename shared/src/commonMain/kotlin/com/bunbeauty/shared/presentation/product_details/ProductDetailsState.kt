package com.bunbeauty.shared.presentation.product_details

import com.bunbeauty.shared.domain.model.cart.CartCostAndCount

data class ProductDetailsState(
    val cartCostAndCount: CartCostAndCount? = null,
    val menuProduct: MenuProduct? = null,
    val state: State = State.LOADING
) {

    enum class State {
        SUCCESS,
        ERROR,
        LOADING,
    }

    data class MenuProduct(
        val uuid: String,
        val photoLink: String,
        val name: String,
        val size: String,
        val oldPrice: String?,
        val newPrice: String,
        val description: String
    )

}

