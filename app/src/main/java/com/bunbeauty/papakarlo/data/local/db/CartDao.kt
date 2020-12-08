package com.bunbeauty.papakarlo.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.papakarlo.data.model.CartProduct

@Dao
interface CartDao: BaseDao<CartProduct> {

    @Query("SELECT * FROM CartProduct")
    fun getCart(): LiveData<List<CartProduct>>

}