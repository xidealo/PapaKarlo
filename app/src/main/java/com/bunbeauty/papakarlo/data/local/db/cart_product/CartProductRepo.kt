package com.bunbeauty.papakarlo.data.local.db.cart_product

import com.bunbeauty.papakarlo.data.model.CartProduct
import kotlinx.coroutines.Deferred

interface CartProductRepo {
    suspend fun insertCartProduct(cartProduct: CartProduct): CartProduct
    suspend fun insertCartProductAsync(cartProduct: CartProduct): Deferred<CartProduct>
}