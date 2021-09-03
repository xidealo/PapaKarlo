package com.bunbeauty.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.bunbeauty.domain.model.entity.product.MenuProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuProductDao : BaseDao<MenuProductEntity> {

//    @Transaction
//    suspend fun refreshMenuProductList(menuProductList: List<MenuProductEntity>) {
//        val localMenuProduct =
//
//        deleteAll()
//        insertAll(menuProductList)
//    }

    // OBSERVE

    @Query("SELECT * FROM MenuProductEntity ORDER BY productCode, cost")
    fun observeMenuProductList(): Flow<List<MenuProductEntity>>

    @Query("SELECT * FROM MenuProductEntity WHERE uuid = :menuProductUuid ")
    fun observeMenuProductByUuid(menuProductUuid: String): Flow<MenuProductEntity?>

    @Query("SELECT * FROM MenuProductEntity WHERE productCode = :productCode ORDER BY cost")
    fun observeMenuProductListByCode(productCode: String): Flow<List<MenuProductEntity>>

    // GET

    @Query("SELECT * FROM MenuProductEntity WHERE uuid = :menuProductUuid ")
    suspend fun getMenuProductByUuid(menuProductUuid: String): MenuProductEntity?

    // DELETE

    @Transaction
    @Query("DELETE FROM MenuProductEntity")
    suspend fun deleteAll()
}