package com.bunbeauty.shared.data.mapper.cart_product

import com.bunbeauty.core.model.cart.CartProduct
import com.bunbeauty.shared.db.CartProductEntity
import com.bunbeauty.shared.db.CartProductWithMenuProductEntity
import com.bunbeauty.shared.db.MenuProductWithCategoryEntity

interface ICartProductMapper {
    fun toCartProduct(
        cartProductWithMenuProductEntityList: List<CartProductWithMenuProductEntity>,
        menuProductWithCategoryEntityList: List<MenuProductWithCategoryEntity>,
    ): CartProduct

    fun toEntityModel(cartProduct: CartProduct): CartProductEntity
}
