package com.bunbeauty.domain.repository.cart_product

import com.bunbeauty.data.model.CartProduct
import kotlinx.coroutines.flow.Flow

interface CartProductRepo {
    suspend fun insert(cartProduct: CartProduct): CartProduct

    fun getCartProductListFlow(): Flow<List<CartProduct>>

    suspend fun getCartProductList(): List<CartProduct>

    suspend fun update(cartProduct: CartProduct)

    suspend fun delete(cartProduct: CartProduct)
}