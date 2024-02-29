package com.bunbeauty.shared.data.mapper.cart_product

import com.bunbeauty.shared.db.CartProductEntity
import com.bunbeauty.shared.db.CartProductWithMenuProductEntity
import com.bunbeauty.shared.db.MenuProductWithCategoryEntity
import com.bunbeauty.shared.domain.model.cart.CartProduct

interface ICartProductMapper {

    fun toCartProduct(
        cartProductWithMenuProductEntityList: List<CartProductWithMenuProductEntity>,
        menuProductWithCategoryEntityList: List<MenuProductWithCategoryEntity>,
    ): CartProduct
    fun toEntityModel(cartProduct: CartProduct): CartProductEntity
}