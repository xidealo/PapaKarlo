package com.bunbeauty.data.mapper.cart_product

import com.bunbeauty.data.mapper.menu_product.IMenuProductMapper
import com.bunbeauty.domain.model.firebase.CartProductFirebase
import com.bunbeauty.domain.model.ui.CartProduct
import javax.inject.Inject

class CartProductMapper @Inject constructor(
    private val menuProductMapper: IMenuProductMapper
): ICartProductMapper {

    override fun toFirebaseModel(cartProduct: CartProduct): CartProductFirebase {
        return CartProductFirebase(
            count = cartProduct.count,
            menuProduct = menuProductMapper.toFirebaseModel(cartProduct.menuProduct)
        )
    }
}