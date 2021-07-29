package com.bunbeauty.data.mapper.cart_product

import com.bunbeauty.domain.model.firebase.CartProductFirebase
import com.bunbeauty.domain.model.ui.CartProduct

interface ICartProductMapper {

    fun toFirebaseModel(cartProduct: CartProduct): CartProductFirebase
}