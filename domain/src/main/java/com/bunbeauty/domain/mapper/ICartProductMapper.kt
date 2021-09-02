package com.bunbeauty.domain.mapper

import com.bunbeauty.domain.model.entity.product.CartProductEntity
import com.bunbeauty.domain.model.entity.product.CartProductWithMenuProduct
import com.bunbeauty.domain.model.ui.product.CartProduct
import com.bunbeauty.domain.model.ui.product.OrderProduct

interface ICartProductMapper {

    fun toUIModel(cartProductWithMenuProduct: CartProductWithMenuProduct): CartProduct
    fun toEntityModel(cartProduct: CartProduct): CartProductEntity
    fun toOrderProduct(cartProduct: CartProduct): OrderProduct
}