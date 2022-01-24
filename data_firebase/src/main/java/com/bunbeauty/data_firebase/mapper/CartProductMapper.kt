package com.bunbeauty.data_firebase.mapper

import com.bunbeauty.domain.model.product.CartProduct
import com.bunbeauty.domain.model.product.OrderMenuProduct
import com.bunbeauty.domain.model.product.OrderProduct
import com.example.domain_firebase.mapper.ICartProductMapper
import com.example.domain_firebase.mapper.IMenuProductMapper
import com.example.domain_firebase.model.entity.product.CartProductEntity
import com.example.domain_firebase.model.entity.product.CartProductWithMenuProduct
import javax.inject.Inject

class CartProductMapper @Inject constructor(private val menuProductMapper: IMenuProductMapper) :
    ICartProductMapper {

    override fun toEntityModel(cartProduct: CartProduct): CartProductEntity {
        return CartProductEntity(
            uuid = cartProduct.uuid,
            count = cartProduct.count,
            menuProductUuid = cartProduct.product.uuid,
        )
    }

    override fun toUIModel(cartProductWithMenuProduct: CartProductWithMenuProduct): CartProduct {
        return CartProduct(
            uuid = cartProductWithMenuProduct.cartProductEntity.uuid,
            count = cartProductWithMenuProduct.cartProductEntity.count,
            product = menuProductMapper.toUIModel(cartProductWithMenuProduct.menuProductEntity),
        )
    }

    override fun toOrderProduct(cartProduct: CartProduct): OrderProduct {
        return OrderProduct(
            uuid = cartProduct.uuid,
            count = cartProduct.count,
            product = OrderMenuProduct(
                name = cartProduct.product.name,
                newPrice = cartProduct.product.newPrice,
                oldPrice = cartProduct.product.oldPrice,
                utils = cartProduct.product.utils,
                nutrition = cartProduct.product.nutrition,
                description = cartProduct.product.description,
                comboDescription = cartProduct.product.comboDescription,
                photoLink = cartProduct.product.photoLink
            ),
        )
    }

}