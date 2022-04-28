package com.bunbeauty.domain.model.cart

import com.bunbeauty.domain.model.product.MenuProduct
import com.bunbeauty.domain.model.product.ProductPosition

data class CartProduct(
    val uuid: String,
    override val count: Int,
    override val product: MenuProduct
) : ProductPosition()
