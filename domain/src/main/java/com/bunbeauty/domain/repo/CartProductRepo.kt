package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.local.CartProduct
import kotlinx.coroutines.flow.Flow

interface CartProductRepo {

    val cartProductList: Flow<List<CartProduct>>

    suspend fun getCartProductList(): List<CartProduct>

    suspend fun getCartProduct(cartProductUuid: String): CartProduct?

    suspend fun insert(cartProduct: CartProduct)

    suspend fun update(cartProduct: CartProduct)

    suspend fun delete(cartProduct: CartProduct)
}