package com.example.data_api.mapper

import com.bunbeauty.domain.model.product.CartProduct
import com.example.domain_api.mapper.ICartProductMapper
import com.example.domain_api.mapper.IMenuProductMapper
import com.example.domain_api.model.entity.product.CartProductEntity
import com.example.domain_api.model.entity.product.CartProductWithMenuProduct
import javax.inject.Inject

class CartProductMapper @Inject constructor(
    private val menuProductMapper: IMenuProductMapper
) : ICartProductMapper {

    override fun toModel(cartProductWithMenuProduct: CartProductWithMenuProduct): CartProduct {
        return CartProduct(
            uuid = cartProductWithMenuProduct.cartProductEntity.uuid,
            count = cartProductWithMenuProduct.cartProductEntity.count,
            product = menuProductMapper.toModel(cartProductWithMenuProduct.menuProductEntity),
        )
    }

    override fun toEntityModel(cartProduct: CartProduct): CartProductEntity {
        return CartProductEntity(
            uuid = cartProduct.uuid,
            count = cartProduct.count,
            menuProductUuid = cartProduct.product.uuid,
        )
    }
}