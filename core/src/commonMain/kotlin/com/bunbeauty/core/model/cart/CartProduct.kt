package com.bunbeauty.core.model.cart

import com.bunbeauty.core.model.addition.CartProductAddition
import com.bunbeauty.core.model.product.MenuProduct
import com.bunbeauty.core.model.product.ProductPosition

data class CartProduct(
    val uuid: String,
    override val count: Int,
    override val product: MenuProduct,
    val additionList: List<CartProductAddition>,
) : ProductPosition()
