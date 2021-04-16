package com.bunbeauty.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.bunbeauty.data.model.MenuProduct
import com.bunbeauty.data.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuProductDao : BaseDao<MenuProduct> {

    @Transaction
    @Query("DELETE FROM MenuProduct")
    fun deleteAll()

    @Query("SELECT * FROM MenuProduct ORDER BY productCode, cost")
    fun getMenuProductListFlow(): Flow<List<MenuProduct>>

    @Query("SELECT * FROM MenuProduct WHERE productCode = :productCode ORDER BY cost")
    fun getMenuProductListByCodeLiveData(productCode: String): Flow<List<MenuProduct>>
}