package com.bunbeauty.shared.data.dao.menu_product

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.db.MenuProductEntity
import com.bunbeauty.shared.db.MenuProductWithCategoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class MenuProductDao(
    foodDeliveryDatabase: FoodDeliveryDatabase,
) : IMenuProductDao {
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
                    isRecommended = menuProduct.isRecommended,
                )
            }
        }
    }

    override suspend fun getMenuProductWithCategoryList(): List<MenuProductWithCategoryEntity> =
        menuProductEntityQueries.getMenuProductList().awaitAsList()

    override suspend fun getMenuProductWithCategoryListByUuid(uuid: String): List<MenuProductWithCategoryEntity> =
        menuProductEntityQueries.getMenuProductWithCategoryListByUuid(uuid).awaitAsList()

    override fun observeMenuProductList(): Flow<List<MenuProductWithCategoryEntity>> =
        menuProductEntityQueries
            .getMenuProductList()
            .asFlow()
            .mapToList(Dispatchers.Default)

    override fun observeMenuProductByUuid(uuid: String): Flow<MenuProductEntity?> =
        menuProductEntityQueries
            .getMenuProductByUuid(uuid)
            .asFlow()
            .mapToOneOrNull(Dispatchers.Default)

    override suspend fun getMenuProductByUuid(uuid: String): MenuProductEntity? =
        menuProductEntityQueries.getMenuProductByUuid(uuid).awaitAsOneOrNull()
}
