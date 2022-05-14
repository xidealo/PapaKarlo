package com.bunbeauty.shared.data.mapper.menuProduct

import com.bunbeauty.shared.data.network.model.MenuProductServer
import com.bunbeauty.shared.db.CategoryEntity
import com.bunbeauty.shared.db.MenuProductCategoryReference
import com.bunbeauty.shared.db.MenuProductEntity
import com.bunbeauty.shared.db.MenuProductWithCategoryEntity
import com.bunbeauty.shared.domain.model.category.Category
import com.bunbeauty.shared.domain.model.product.MenuProduct

class MenuProductMapper : IMenuProductMapper {

    override fun toMenuProductCategoryReference(menuProduct: MenuProductServer): List<MenuProductCategoryReference> {
        return menuProduct.categories.map { categoryServer ->
            MenuProductCategoryReference(
                menuProductUuidReference = menuProduct.uuid,
                categoryUuidReference = categoryServer.uuid
            )
        }
    }

    override fun toMenuProductEntity(menuProduct: MenuProductServer): MenuProductEntity {
        return MenuProductEntity(
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
            visible = menuProduct.isVisible,
        )
    }

    override fun toCategoryEntityList(menuProductServerList: List<MenuProductServer>): List<CategoryEntity> {
        return menuProductServerList.flatMap { menuProductServer ->
            menuProductServer.categories
        }.toSet()
            .map { categoryServer ->
                CategoryEntity(
                    uuid = categoryServer.uuid,
                    name = categoryServer.name,
                    priority = categoryServer.priority,
                )
            }
    }

    override fun toMenuProduct(menuProduct: MenuProductEntity): MenuProduct {
        return MenuProduct(
            uuid = menuProduct.uuid,
            name = menuProduct.name,
            newPrice = menuProduct.newPrice,
            oldPrice = menuProduct.oldPrice,
            utils = menuProduct.utils,
            nutrition = menuProduct.nutrition,
            description = menuProduct.description,
            comboDescription = menuProduct.comboDescription,
            photoLink = menuProduct.photoLink,
            categoryList = emptyList()
        )
    }

    override fun toMenuProduct(menuProductServer: MenuProductServer): MenuProduct {
        return MenuProduct(
            uuid = menuProductServer.uuid,
            name = menuProductServer.name,
            newPrice = menuProductServer.newPrice,
            oldPrice = menuProductServer.oldPrice,
            utils = menuProductServer.utils,
            nutrition = menuProductServer.nutrition,
            description = menuProductServer.description,
            comboDescription = menuProductServer.comboDescription,
            photoLink = menuProductServer.photoLink,
            categoryList = menuProductServer.categories.map { categoryServer ->
                Category(
                    uuid = categoryServer.uuid,
                    name = categoryServer.name,
                    priority = categoryServer.priority,
                )
            }
        )
    }

    override fun toMenuProductList(menuProductWithCategoryEntityList: List<MenuProductWithCategoryEntity>): List<MenuProduct> {
        return menuProductWithCategoryEntityList.groupBy { menuProductWithCategoryEntity ->
            menuProductWithCategoryEntity.uuid
        }.map { (_, groupedMenuProductWithCategoryEntityList) ->
            val firstMenuProductWithCategoryEntity =
                groupedMenuProductWithCategoryEntityList.first()
            MenuProduct(
                uuid = firstMenuProductWithCategoryEntity.uuid,
                name = firstMenuProductWithCategoryEntity.name,
                newPrice = firstMenuProductWithCategoryEntity.newPrice,
                oldPrice = firstMenuProductWithCategoryEntity.oldPrice,
                utils = firstMenuProductWithCategoryEntity.utils,
                nutrition = firstMenuProductWithCategoryEntity.nutrition,
                description = firstMenuProductWithCategoryEntity.description,
                comboDescription = firstMenuProductWithCategoryEntity.comboDescription,
                photoLink = firstMenuProductWithCategoryEntity.photoLink,
                categoryList = groupedMenuProductWithCategoryEntityList.map { menuProductWithCategoryEntity ->
                    Category(
                        uuid = menuProductWithCategoryEntity.categoryUuid,
                        name = menuProductWithCategoryEntity.categoryName,
                        priority = menuProductWithCategoryEntity.priority
                    )
                }
            )
        }
    }
}