package com.bunbeauty.shared.data.dao.menu_product

import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.db.MenuProductEntity
import com.bunbeauty.shared.db.MenuProductWithCategoryEntity
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.flow.Flow

class MenuProductDao(foodDeliveryDatabase: FoodDeliveryDatabase) : IMenuProductDao {

    private val menuProductEntityQueries = foodDeliveryDatabase.menuProductEntityQueries

    override suspend fun insertMenuProductList(menuProductList: List<MenuProductEntity>) {
        menuProductEntityQueries.transaction {
            menuProductList.forEach { menuProduct ->
                menuProductEntityQueries.insertMenuProduct(
                    uuid = menuProduct.uuid,
                    name = menuProduct.name,
                    newPrice = menuProduct.newPrice,
                    oldPrice = menuProduct.oldPrice,
                    utils = menuProduct.utils,
                    nutrition = menuProduct.nutrition,
                    description = menuProduct.description,
                    comboDescription = menuProduct.comboDescription,
                    photoLink = menuProduct.photoLink,
                    barcode = menuProduct.barcode,
                    visible = menuProduct.visible,
                    isRecommended = menuProduct.isRecommended
                )
            }
        }
    }

    override suspend fun getMenuProductWithCategoryList(): List<MenuProductWithCategoryEntity> {
        return menuProductEntityQueries.getMenuProductList().executeAsList()
    }

    override suspend fun getMenuProductWithCategoryListByUuid(uuid: String): List<MenuProductWithCategoryEntity> {
        return menuProductEntityQueries.getMenuProductWithCategoryListByUuid(uuid).executeAsList()
    }

    override fun observeMenuProductList(): Flow<List<MenuProductWithCategoryEntity>> {
        return menuProductEntityQueries.getMenuProductList().asFlow().mapToList()
    }

    override fun observeMenuProductByUuid(uuid: String): Flow<MenuProductEntity?> {
        return menuProductEntityQueries.getMenuProductByUuid(uuid).asFlow().mapToOneOrNull()
    }

    override suspend fun getMenuProductByUuid(uuid: String): MenuProductEntity? {
        return menuProductEntityQueries.getMenuProductByUuid(uuid).executeAsOneOrNull()
    }
}
