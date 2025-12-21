package com.bunbeauty.shared.data.dao.cart_product

import com.bunbeauty.shared.db.CartProductEntity
import com.bunbeauty.shared.db.CartProductWithMenuProductEntity
import kotlinx.coroutines.flow.Flow

interface ICartProductDao {
    suspend fun insertCartProduct(cartProductEntity: CartProductEntity)

    fun observeCartProductList(): Flow<List<CartProductWithMenuProductEntity>>

    suspend fun getCartProductList(): List<CartProductWithMenuProductEntity>

    suspend fun getCartProductByUuid(uuid: String): List<CartProductWithMenuProductEntity>

    suspend fun getCartProductByMenuProductUuid(menuProductUuid: String): List<CartProductWithMenuProductEntity>

    suspend fun updateCartProductCountByUuid(
        uuid: String,
        count: Int,
    )

    suspend fun deleteCartProductByUuid(uuid: String)

    suspend fun deleteAllCartProducts()
}
