package com.bunbeauty.papakarlo.data.local.db.cart_product

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.papakarlo.data.local.db.BaseDao
import com.bunbeauty.papakarlo.data.model.CartProduct
import kotlinx.coroutines.Deferred

@Dao
interface CartProductDao : BaseDao<CartProduct> {

    @Query("SELECT * FROM CartProduct WHERE orderUuid = \"\" ")
    fun getCartProductListLiveData(): LiveData<List<CartProduct>>

    @Query("SELECT * FROM CartProduct WHERE orderUuid = \"\" ")
    fun getCartProductList(): List<CartProduct>
}