package com.bunbeauty.shared.domain.model.cart

import com.bunbeauty.shared.domain.model.product.MenuProduct
import com.bunbeauty.shared.domain.model.product.ProductPosition

data class CartProduct(
    val uuid: String,
    override val count: Int,
    override val product: MenuProduct
) : ProductPosition()
