package com.bunbeauty.data.mapper.cart_product

import com.bunbeauty.data.database.entity.product.CartProductEntity
import com.bunbeauty.data.database.entity.product.CartProductWithMenuProduct
import com.bunbeauty.domain.model.product.CartProduct

interface ICartProductMapper {

    fun toModel(cartProductWithMenuProduct: CartProductWithMenuProduct): CartProduct
    fun toEntityModel(cartProduct: CartProduct): CartProductEntity
}