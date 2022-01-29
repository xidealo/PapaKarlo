package com.bunbeauty.data.mapper.menuProduct

import com.bunbeauty.data.database.entity.product.MenuProductEntity
import com.bunbeauty.data.database.entity.product_with_category.MenuProductWithCategory
import com.bunbeauty.data.mapper.category.ICategoryMapper
import com.bunbeauty.data.network.model.MenuProductServer
import com.bunbeauty.domain.model.product.MenuProduct
import javax.inject.Inject

class MenuProductMapper @Inject constructor(private val categoryMapper: ICategoryMapper) :
    IMenuProductMapper {

    override fun toEntityModel(menuProduct: MenuProductServer): MenuProductWithCategory {
        return MenuProductWithCategory(
            menuProduct = MenuProductEntity(
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
            ),
            categoryList = menuProduct.categories.map(categoryMapper::toEntityModel)
        )
    }

    override fun toModel(menuProduct: MenuProductEntity): MenuProduct {
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

    override fun toModel(menuProduct: MenuProductWithCategory): MenuProduct {
        return MenuProduct(
            uuid = menuProduct.menuProduct.uuid,
            name = menuProduct.menuProduct.name,
            newPrice = menuProduct.menuProduct.newPrice,
            oldPrice = menuProduct.menuProduct.oldPrice,
            utils = menuProduct.menuProduct.utils,
            nutrition = menuProduct.menuProduct.nutrition,
            description = menuProduct.menuProduct.description,
            comboDescription = menuProduct.menuProduct.comboDescription,
            photoLink = menuProduct.menuProduct.photoLink,
            categoryList = menuProduct.categoryList.map(categoryMapper::toModel),
        )
    }
}