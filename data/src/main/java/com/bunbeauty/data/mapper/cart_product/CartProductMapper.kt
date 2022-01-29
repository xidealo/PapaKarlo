package com.bunbeauty.data.mapper.cart_product

import com.bunbeauty.data.database.entity.product.CartProductEntity
import com.bunbeauty.data.database.entity.product.CartProductWithMenuProduct
import com.bunbeauty.data.mapper.menuProduct.IMenuProductMapper
import com.bunbeauty.domain.model.product.CartProduct
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