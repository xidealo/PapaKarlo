package com.bunbeauty.data.mapper.menuProduct

import com.bunbeauty.data.network.model.MenuProductServer
import com.bunbeauty.domain.model.category.Category
import com.bunbeauty.domain.model.product.MenuProduct
import database.MenuProductCategoryReference
import database.MenuProductEntity
import database.MenuProductWithCategoryEntity

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