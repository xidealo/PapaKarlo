package com.bunbeauty.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.domain.model.CartProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface CartProductDao : BaseDao<CartProduct> {

    @Query("SELECT * FROM CartProduct WHERE orderId IS NULL")
    fun getCartProductListLiveData(): Flow<List<CartProduct>>

    @Query("SELECT * FROM CartProduct WHERE orderId IS NULL")
    fun getCartProductList(): List<CartProduct>
}