package com.bunbeauty.data.mapper

import com.bunbeauty.common.Mapper
import com.bunbeauty.data.model.CartProduct
import com.bunbeauty.data.model.firebase.CartProductFirebase
import javax.inject.Inject

class CartProductMapper @Inject constructor(
    private val menuProductMapper: MenuProductMapper
) : Mapper<CartProductFirebase, CartProduct> {

    override fun from(e: CartProduct): CartProductFirebase {
        return CartProductFirebase(
            menuProductMapper.from(e.menuProduct),
            e.count
        )
    }

    /**
     * Set uuid, id after convert
     */
    override fun to(t: CartProductFirebase): CartProduct {
        return CartProduct(
            "empty uuid",
            menuProduct = menuProductMapper.to(t.menuProduct),
            count = t.count,
            orderId = t.orderId
        )
    }
}