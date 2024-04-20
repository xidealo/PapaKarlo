package com.bunbeauty.shared.data.mapper.menuProduct

import com.bunbeauty.shared.data.network.model.MenuProductServer
import com.bunbeauty.shared.db.AdditionEntity
import com.bunbeauty.shared.db.AdditionGroupEntity
import com.bunbeauty.shared.db.CategoryEntity
import com.bunbeauty.shared.db.MenuProductCategoryReference
import com.bunbeauty.shared.db.MenuProductEntity
import com.bunbeauty.shared.db.MenuProductWithCategoryEntity
import com.bunbeauty.shared.domain.model.addition.Addition
import com.bunbeauty.shared.domain.model.addition.AdditionGroup
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
            isRecommended = menuProduct.isRecommended
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

    override fun toAdditionEntityList(menuProductServerList: List<MenuProductServer>): List<AdditionEntity> {
        return menuProductServerList.flatMap { menuProductServer ->
            menuProductServer.additionGroupServers.flatMap { additionGroupServer ->
                additionGroupServer.additionServerList.map { additionServer ->
                    AdditionEntity(
                        uuid = additionServer.uuid,
                        name = additionServer.name,
                        price = additionServer.price,
                        isVisible = additionServer.isVisible,
                        isSelected = additionServer.isSelected,
                        additionGroupUuid = additionGroupServer.uuid,
                        photoLink = additionServer.photoLink,
                        fullName = additionServer.fullName,
                        priority = additionServer.priority,
                    )
                }
            }
        }
    }

    override fun toAdditionGroupEntityList(menuProductServerList: List<MenuProductServer>): List<AdditionGroupEntity> {
        return menuProductServerList.flatMap { menuProductServer ->
            menuProductServer.additionGroupServers.map { additionServer ->
                AdditionGroupEntity(
                    uuid = additionServer.uuid,
                    name = additionServer.name,
                    isVisible = additionServer.isVisible,
                    menuProductUuid = menuProductServer.uuid,
                    priority = additionServer.priority,
                    singleChoice = additionServer.singleChoice,
                )
            }
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
            categoryList = emptyList(),
            visible = menuProduct.visible,
            isRecommended = menuProduct.isRecommended,
            additionGroups = emptyList()
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
            },
            visible = menuProductServer.isVisible,
            isRecommended = menuProductServer.isRecommended,
            additionGroups = menuProductServer.additionGroupServers.map { additionGroupServer ->
                AdditionGroup(
                    additionList = additionGroupServer.additionServerList.map { additionServer ->
                        Addition(
                            isSelected = additionServer.isSelected,
                            isVisible = additionServer.isVisible,
                            name = additionServer.name,
                            photoLink = additionServer.photoLink,
                            price = additionServer.price,
                            uuid = additionServer.uuid,
                            additionGroupUuid = additionGroupServer.uuid,
                            fullName = additionServer.fullName,
                            priority = additionServer.priority
                        )
                    },
                    isVisible = additionGroupServer.isVisible,
                    name = additionGroupServer.name,
                    singleChoice = additionGroupServer.singleChoice,
                    uuid = additionGroupServer.uuid,
                    priority = additionGroupServer.priority
                )
            }
        )
    }

    override fun toMenuProductList(
        menuProductWithCategoryEntityList: List<MenuProductWithCategoryEntity>,
    ): List<MenuProduct> {
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
                        priority = menuProductWithCategoryEntity.categoryPriority
                    )
                },
                visible = firstMenuProductWithCategoryEntity.visible,
                isRecommended = firstMenuProductWithCategoryEntity.isRecommended,
                additionGroups = emptyList()
            )
        }
    }
}