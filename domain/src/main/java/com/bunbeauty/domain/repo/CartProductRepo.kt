package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.product.CartProduct
import kotlinx.coroutines.flow.Flow

interface CartProductRepo {

    fun observeCartProductList(): Flow<List<CartProduct>>

    suspend fun getCartProductList(): List<CartProduct>

    suspend fun getCartProductByMenuProductUuid(menuProductUuid: String): CartProduct?

    suspend fun saveAsCartProduct(menuProductUuid: String): CartProduct?

    suspend fun updateCartProductCount(cartProductUuid: String, count: Int)

    suspend fun deleteCartProduct(cartProduct: CartProduct)

    suspend fun deleteCartProductList(cartProductList: List<CartProduct>)
}