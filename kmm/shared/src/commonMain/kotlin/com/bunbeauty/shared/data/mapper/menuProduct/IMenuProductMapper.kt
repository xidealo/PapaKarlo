package com.bunbeauty.shared.data.mapper.menuProduct

import com.bunbeauty.shared.data.network.model.MenuProductServer
import com.bunbeauty.shared.domain.model.product.MenuProduct
import database.CategoryEntity
import database.MenuProductCategoryReference
import database.MenuProductEntity
import database.MenuProductWithCategoryEntity

interface IMenuProductMapper {

    fun toMenuProductCategoryReference(menuProduct: MenuProductServer): List<MenuProductCategoryReference>
    fun toMenuProductEntity(menuProduct: MenuProductServer): MenuProductEntity
    fun toCategoryEntityList(menuProductServerList: List<MenuProductServer>): List<CategoryEntity>
    fun toMenuProduct(menuProduct: MenuProductEntity): MenuProduct
    fun toMenuProduct(menuProductServer: MenuProductServer): MenuProduct
    fun toMenuProductList(menuProductWithCategoryEntityList: List<MenuProductWithCategoryEntity>): List<MenuProduct>
}