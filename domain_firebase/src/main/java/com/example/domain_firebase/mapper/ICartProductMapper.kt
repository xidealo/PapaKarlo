package com.example.domain_firebase.mapper

import com.bunbeauty.domain.model.product.CartProduct
import com.bunbeauty.domain.model.product.OrderProduct
import com.example.domain_firebase.model.entity.product.CartProductEntity
import com.example.domain_firebase.model.entity.product.CartProductWithMenuProduct

interface ICartProductMapper {

    fun toUIModel(cartProductWithMenuProduct: CartProductWithMenuProduct): CartProduct
    fun toEntityModel(cartProduct: CartProduct): CartProductEntity
    fun toOrderProduct(cartProduct: CartProduct): OrderProduct
}