package com.bunbeauty.data.dao.menu_product

import database.MenuProductEntity
import database.MenuProductWithCategoryEntity
import kotlinx.coroutines.flow.Flow

interface IMenuProductDao {

    suspend fun insertMenuProductList(menuProductList: List<MenuProductEntity>)

    suspend fun getMenuProductWithCategoryList(): List<MenuProductWithCategoryEntity>

    fun observeMenuProductList(): Flow<List<MenuProductWithCategoryEntity>>

    fun observeMenuProductByUuid(uuid: String): Flow<MenuProductEntity?>

    suspend fun getMenuProductByUuid(uuid: String): MenuProductEntity?

}