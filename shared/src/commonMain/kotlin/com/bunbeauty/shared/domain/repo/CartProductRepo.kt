package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.cart.CartProduct
import kotlinx.coroutines.flow.Flow

interface CartProductRepo {

    fun observeCartProductList(): Flow<List<CartProduct>>

    suspend fun getCartProductList(): List<CartProduct>

    suspend fun getCartProductByMenuProductUuid(menuProductUuid: String): CartProduct?

    suspend fun saveAsCartProduct(menuProductUuid: String): CartProduct?

    suspend fun updateCartProductCount(cartProductUuid: String, count: Int): CartProduct?

    suspend fun deleteCartProduct(cartProductUuid: String)

    suspend fun deleteAllCartProducts()
}