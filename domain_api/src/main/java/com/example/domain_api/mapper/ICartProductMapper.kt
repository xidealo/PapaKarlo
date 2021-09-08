package com.example.domain_api.mapper

import com.bunbeauty.domain.model.product.CartProduct
import com.example.domain_api.model.entity.CartProductEntity
import com.example.domain_api.model.entity.CartProductWithMenuProduct

interface ICartProductMapper {

    fun toModel(cartProductWithMenuProduct: CartProductWithMenuProduct): CartProduct
    fun toEntityModel(cartProduct: CartProduct): CartProductEntity
}