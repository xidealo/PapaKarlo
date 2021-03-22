package com.bunbeauty.domain.repository.cart_product

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.data.model.CartProduct
import com.bunbeauty.domain.repository.BaseDao

@Dao
interface CartProductDao : BaseDao<CartProduct> {

    @Query("SELECT * FROM CartProduct WHERE orderId IS NULL")
    fun getCartProductListLiveData(): LiveData<List<CartProduct>>

    @Query("SELECT * FROM CartProduct WHERE orderId IS NULL")
    fun getCartProductList(): List<CartProduct>
}