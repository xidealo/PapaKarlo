package com.bunbeauty.shared.data.mapper.cart_product

import com.bunbeauty.shared.db.CartProductEntity
import com.bunbeauty.shared.db.CartProductWithMenuProductEntity
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.product.MenuProduct

class CartProductMapper : ICartProductMapper {

    override fun toModel(cartProductWithMenuProductEntity: CartProductWithMenuProductEntity): CartProduct {
        return CartProduct(
            uuid = cartProductWithMenuProductEntity.cartProductUuid,
            count = cartProductWithMenuProductEntity.count,
            product = MenuProduct(
                uuid = cartProductWithMenuProductEntity.uuid,
                name = cartProductWithMenuProductEntity.name,
                newPrice = cartProductWithMenuProductEntity.newPrice,
                oldPrice = cartProductWithMenuProductEntity.oldPrice,
                utils = cartProductWithMenuProductEntity.utils,
                nutrition = cartProductWithMenuProductEntity.nutrition,
                description = cartProductWithMenuProductEntity.description,
                comboDescription = cartProductWithMenuProductEntity.comboDescription,
                photoLink = cartProductWithMenuProductEntity.photoLink,
                categoryList = emptyList()
            )
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