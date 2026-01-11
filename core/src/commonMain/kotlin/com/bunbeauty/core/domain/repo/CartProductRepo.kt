package com.bunbeauty.core.domain.repo

import com.bunbeauty.core.model.cart.CartProduct
import kotlinx.coroutines.flow.Flow

interface CartProductRepo {
    fun observeCartProductList(): Flow<List<CartProduct>>

    suspend fun getCartProductList(): List<CartProduct>

    suspend fun getCartProduct(cartProductUuid: String): CartProduct?

    suspend fun getCartProductListByMenuProductUuid(menuProductUuid: String): List<CartProduct>

    suspend fun saveAsCartProduct(menuProductUuid: String): String

    suspend fun updateCartProductCount(
        cartProductUuid: String,
        count: Int,
    )

    suspend fun deleteCartProduct(cartProductUuid: String)

    suspend fun deleteAllCartProducts()
}