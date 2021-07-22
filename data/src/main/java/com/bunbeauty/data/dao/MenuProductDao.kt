package com.bunbeauty.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.bunbeauty.domain.model.ui.MenuProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuProductDao : BaseDao<MenuProduct> {

    @Transaction
    @Query("DELETE FROM MenuProduct")
    fun deleteAll()

    @Query("SELECT * FROM MenuProduct ORDER BY productCode, cost")
    fun getMenuProductListFlow(): Flow<List<MenuProduct>>

    @Query("SELECT * FROM MenuProduct WHERE uuid = :menuProductUuid ")
    fun getMenuProductFlow(menuProductUuid: String): Flow<MenuProduct?>

    @Query("SELECT * FROM MenuProduct WHERE uuid = :menuProductUuid ")
    fun getMenuProduct(menuProductUuid: String): MenuProduct?

    @Query("SELECT * FROM MenuProduct WHERE productCode = :productCode ORDER BY cost")
    fun getMenuProductListByCodeLiveData(productCode: String): Flow<List<MenuProduct>>
}