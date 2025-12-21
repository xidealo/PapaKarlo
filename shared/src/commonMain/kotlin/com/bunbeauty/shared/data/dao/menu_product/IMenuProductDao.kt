package com.bunbeauty.shared.data.dao.menu_product

import com.bunbeauty.shared.db.MenuProductEntity
import com.bunbeauty.shared.db.MenuProductWithCategoryEntity
import kotlinx.coroutines.flow.Flow

interface IMenuProductDao {
    suspend fun insertMenuProductList(menuProductList: List<MenuProductEntity>)

    suspend fun getMenuProductWithCategoryList(): List<MenuProductWithCategoryEntity>

    suspend fun getMenuProductWithCategoryListByUuid(uuid: String): List<MenuProductWithCategoryEntity>

    fun observeMenuProductList(): Flow<List<MenuProductWithCategoryEntity>>

    fun observeMenuProductByUuid(uuid: String): Flow<MenuProductEntity?>

    suspend fun getMenuProductByUuid(uuid: String): MenuProductEntity?
}
