package com.bunbeauty.data.mapper.firebase

import com.bunbeauty.data.mapper.Mapper
import com.bunbeauty.domain.model.firebase.CartProductFirebase
import com.bunbeauty.domain.model.ui.CartProduct
import javax.inject.Inject

class CartProductMapper @Inject constructor(
    private val menuProductMapper: MenuProductMapper
) : Mapper<CartProductFirebase, CartProduct> {

    override fun from(model: CartProductFirebase): CartProduct {
        return CartProduct(
            "empty uuid",
            menuProduct = menuProductMapper.from(model.menuProduct),
            count = model.count,
            orderUuid = model.orderId
        )
    }

    /**
     * Set uuid, id after convert
     */
    override fun to(model: CartProduct): CartProductFirebase {
        return CartProductFirebase(
            menuProductMapper.to(model.menuProduct),
            model.count
        )
    }
}