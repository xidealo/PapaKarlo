package com.bunbeauty.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.bunbeauty.domain.model.entity.order.CartProductCountEntity
import com.bunbeauty.domain.model.entity.product.CartProductEntity
import com.bunbeauty.domain.model.entity.product.CartProductWithMenuProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface CartProductDao : BaseDao<CartProductEntity> {

    // OBSERVE

    @Query("SELECT * FROM CartProductEntity")
    fun observeCartProductList(): Flow<List<CartProductWithMenuProduct>>

    // GET

    @Query("SELECT * FROM CartProductEntity")
    suspend fun getCartProductList(): List<CartProductWithMenuProduct>

    @Query("SELECT * FROM CartProductEntity WHERE menuProductUuid = :menuProductUuid")
    suspend fun getCartProductByMenuProductUuid(menuProductUuid: String): CartProductWithMenuProduct?

    @Update(entity = CartProductEntity::class)
    suspend fun updateCount(cartProductCountEntity: CartProductCountEntity)
}