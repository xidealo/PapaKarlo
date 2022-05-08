package com.bunbeauty.shared.data.mapper.cart_product

import com.bunbeauty.shared.domain.model.cart.CartProduct
import database.CartProductEntity
import database.CartProductWithMenuProductEntity

interface ICartProductMapper {

    fun toModel(cartProductWithMenuProductEntity: CartProductWithMenuProductEntity): CartProduct
    fun toEntityModel(cartProduct: CartProduct): CartProductEntity
}