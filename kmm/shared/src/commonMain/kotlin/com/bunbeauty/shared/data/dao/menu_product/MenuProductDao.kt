package com.bunbeauty.shared.data.dao.menu_product

import com.bunbeauty.data.FoodDeliveryDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import database.MenuProductEntity
import database.MenuProductWithCategoryEntity
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
                )
            }
        }
    }

    override suspend fun getMenuProductWithCategoryList(): List<MenuProductWithCategoryEntity> {
        return menuProductEntityQueries.getMenuProductList().executeAsList()
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