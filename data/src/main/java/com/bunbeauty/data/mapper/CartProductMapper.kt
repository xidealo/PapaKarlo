package com.bunbeauty.data.mapper

import com.bunbeauty.domain.mapper.ICartProductMapper
import com.bunbeauty.domain.mapper.IMenuProductMapper
import com.bunbeauty.domain.model.entity.product.CartProductEntity
import com.bunbeauty.domain.model.entity.product.CartProductWithMenuProduct
import com.bunbeauty.domain.model.ui.product.CartProduct
import com.bunbeauty.domain.model.ui.product.OrderProduct
import javax.inject.Inject

class CartProductMapper @Inject constructor(private val menuProductMapper: IMenuProductMapper) :
    ICartProductMapper {

    override fun toEntityModel(cartProduct: CartProduct): CartProductEntity {
        return CartProductEntity(
            uuid = cartProduct.uuid,
            count = cartProduct.count,
            menuProductUuid = cartProduct.menuProduct.uuid,
        )
    }

    override fun toUIModel(cartProductWithMenuProduct: CartProductWithMenuProduct): CartProduct {
        return CartProduct(
            uuid = cartProductWithMenuProduct.cartProductEntity.uuid,
            count = cartProductWithMenuProduct.cartProductEntity.count,
            menuProduct = menuProductMapper.toUIModel(cartProductWithMenuProduct.menuProductEntity),
        )
    }

    override fun toOrderProduct(cartProduct: CartProduct): OrderProduct {
        return OrderProduct(
            uuid = cartProduct.uuid,
            count = cartProduct.count,
            menuProduct = cartProduct.menuProduct,
        )
    }

}