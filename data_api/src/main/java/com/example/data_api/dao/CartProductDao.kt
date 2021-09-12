package com.example.data_api.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.bunbeauty.data.BaseDao
import com.example.domain_api.model.entity.product.CartProductCount
import com.example.domain_api.model.entity.product.CartProductEntity
import com.example.domain_api.model.entity.product.CartProductWithMenuProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface CartProductDao : BaseDao<CartProductEntity> {

    // OBSERVE

    @Query("SELECT * FROM CartProductEntity")
    fun observeCartProductList(): Flow<List<CartProductWithMenuProduct>>

    // GET

    @Query("SELECT * FROM CartProductEntity")
    suspend fun getCartProductList(): List<CartProductWithMenuProduct>

    @Query("SELECT * FROM CartProductEntity WHERE uuid = :uuid")
    suspend fun getCartProductByUuid(uuid: String): CartProductWithMenuProduct?

    @Query("SELECT * FROM CartProductEntity WHERE menuProductUuid = :menuProductUuid")
    suspend fun getCartProductByMenuProductUuid(menuProductUuid: String): CartProductWithMenuProduct?

    // UPDATE

    @Update(entity = CartProductEntity::class)
    suspend fun updateCartProductCount(cartProductCount: CartProductCount)

}