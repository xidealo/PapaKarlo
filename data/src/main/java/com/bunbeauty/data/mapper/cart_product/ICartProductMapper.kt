package com.bunbeauty.data.mapper.cart_product

import com.bunbeauty.domain.model.product.CartProduct
import database.CartProductEntity
import database.CartProductWithMenuProductEntity

interface ICartProductMapper {

    fun toModel(cartProductWithMenuProductEntity: CartProductWithMenuProductEntity): CartProduct
    fun toEntityModel(cartProduct: CartProduct): CartProductEntity
}