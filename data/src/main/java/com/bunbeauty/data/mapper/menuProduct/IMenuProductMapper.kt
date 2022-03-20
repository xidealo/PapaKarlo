package com.bunbeauty.data.mapper.menuProduct

import com.bunbeauty.data.network.model.MenuProductServer
import com.bunbeauty.domain.model.product.MenuProduct
import database.MenuProductCategoryReference
import database.MenuProductEntity
import database.MenuProductWithCategoryEntity

interface IMenuProductMapper {

    fun toMenuProductCategoryReference(menuProduct: MenuProductServer): List<MenuProductCategoryReference>
    fun toMenuProductEntity(menuProduct: MenuProductServer): MenuProductEntity
    fun toMenuProduct(menuProduct: MenuProductEntity): MenuProduct
    fun toMenuProductList(menuProductWithCategoryEntityList: List<MenuProductWithCategoryEntity>): List<MenuProduct>
}