package com.bunbeauty.data.dao.cart_product

import database.CartProductEntity
import database.CartProductWithMenuProductEntity
import kotlinx.coroutines.flow.Flow

interface ICartProductDao {

    suspend fun insertCartProduct(cartProductEntity: CartProductEntity)

    fun observeCartProductList(): Flow<List<CartProductWithMenuProductEntity>>

    suspend fun getCartProductList(): List<CartProductWithMenuProductEntity>
    suspend fun getCartProductByUuid(uuid: String): CartProductWithMenuProductEntity?
    suspend fun getCartProductByMenuProductUuid(menuProductUuid: String): CartProductWithMenuProductEntity?

    suspend fun updateCartProductCountByUuid(uuid: String, count: Int)

    suspend fun deleteCartProductByUuid(uuid: String)
    suspend fun deleteAllCartProducts()
}