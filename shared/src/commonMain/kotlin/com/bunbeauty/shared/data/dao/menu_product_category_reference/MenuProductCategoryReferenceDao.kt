package com.bunbeauty.shared.data.dao.menu_product_category_reference

import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.db.MenuProductCategoryReference

class MenuProductCategoryReferenceDao(foodDeliveryDatabase: FoodDeliveryDatabase) :
    IMenuProductCategoryReferenceDao {

    private val menuProductCategoryReferenceQueries =
        foodDeliveryDatabase.menuProductCategoryReferenceQueries

    override suspend fun updateMenuProductReferenceList(menuProductCategoryReferenceList: List<MenuProductCategoryReference>) {
        menuProductCategoryReferenceQueries.transaction {
            menuProductCategoryReferenceList.forEach { menuProductCategoryReference ->
                val localMenuProductCategoryReference =
                    menuProductCategoryReferenceQueries.getMenuProductCategoryReference(
                        menuProductUuidReference = menuProductCategoryReference.menuProductUuidReference,
                        categoryUuidReference = menuProductCategoryReference.categoryUuidReference
                    ).executeAsOneOrNull()
                if (localMenuProductCategoryReference == null) {
                    menuProductCategoryReferenceQueries.insertMenuProductCategoryReference(
                        menuProductUuidReference = menuProductCategoryReference.menuProductUuidReference,
                        categoryUuidReference = menuProductCategoryReference.categoryUuidReference
                    )
                }
            }
            menuProductCategoryReferenceList.groupBy { menuProductCategoryReference ->
                menuProductCategoryReference.menuProductUuidReference
            }.onEach { (menuProductUuid, menuProductCategoryReferenceList) ->
                val localMenuProductCategoryReferenceList =
                    menuProductCategoryReferenceQueries
                        .getMenuProductCategoryReferenceListByMenuProductUuid(menuProductUuid)
                        .executeAsList()
                localMenuProductCategoryReferenceList.forEach { localMenuProductCategoryReference ->
                    if (!menuProductCategoryReferenceList.contains(localMenuProductCategoryReference)) {
                        menuProductCategoryReferenceQueries.deleteMenuProductCategoryReference(
                            menuProductUuidReference = localMenuProductCategoryReference.menuProductUuidReference,
                            categoryUuidReference = localMenuProductCategoryReference.categoryUuidReference
                        )
                    }
                }
            }
        }
    }
}