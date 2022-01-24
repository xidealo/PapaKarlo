package com.example.domain_api.mapper

import com.bunbeauty.domain.model.product.CartProduct
import com.example.domain_api.model.entity.product.CartProductEntity
import com.example.domain_api.model.entity.product.CartProductWithMenuProduct

interface ICartProductMapper {

    fun toModel(cartProductWithMenuProduct: CartProductWithMenuProduct): CartProduct
    fun toEntityModel(cartProduct: CartProduct): CartProductEntity
}