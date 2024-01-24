package com.bunbeauty.shared.data.mapper.cart_product

import com.bunbeauty.shared.db.CartProductEntity
import com.bunbeauty.shared.db.CartProductWithMenuProductEntity
import com.bunbeauty.shared.domain.model.cart.CartProduct

interface ICartProductMapper {

    fun toCartProductList(cartProductWithMenuProductEntityList: List<CartProductWithMenuProductEntity>): List<CartProduct>
    fun toEntityModel(cartProduct: CartProduct): CartProductEntity
}