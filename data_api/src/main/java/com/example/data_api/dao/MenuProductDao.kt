package com.example.data_api.dao

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.data.BaseDao
import com.example.domain_api.model.entity.MenuProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuProductDao: BaseDao<MenuProductEntity> {

    @Query("SELECT * FROM MenuProductEntity WHERE visible = 1")
    fun observeMenuProductList(): Flow<List<MenuProductEntity>>

    @Query("SELECT * FROM MenuProductEntity WHERE uuid = :uuid AND visible = 1")
    fun observeMenuProductByUuid(uuid: String): Flow<MenuProductEntity?>

    @Query("SELECT * FROM MenuProductEntity WHERE uuid = :uuid AND visible = 1")
    suspend fun getMenuProductByUuid(uuid: String): MenuProductEntity?
}