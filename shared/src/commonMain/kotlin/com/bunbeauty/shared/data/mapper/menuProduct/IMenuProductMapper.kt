package com.bunbeauty.shared.data.mapper.menuProduct

import com.bunbeauty.shared.data.network.model.MenuProductServer
import com.bunbeauty.shared.db.AdditionEntity
import com.bunbeauty.shared.db.AdditionGroupEntity
import com.bunbeauty.shared.db.CategoryEntity
import com.bunbeauty.shared.db.MenuProductCategoryReference
import com.bunbeauty.shared.db.MenuProductEntity
import com.bunbeauty.shared.db.MenuProductWithCategoryEntity
import com.bunbeauty.shared.domain.model.product.MenuProduct

interface IMenuProductMapper {
    fun toMenuProductCategoryReference(menuProduct: MenuProductServer): List<MenuProductCategoryReference>
    fun toMenuProductEntity(menuProduct: MenuProductServer): MenuProductEntity
    fun toCategoryEntityList(menuProductServerList: List<MenuProductServer>): List<CategoryEntity>
    fun toAdditionEntityList(menuProductServerList: List<MenuProductServer>): List<AdditionEntity>
    fun toAdditionGroupEntityList(menuProductServerList: List<MenuProductServer>): List<AdditionGroupEntity>
    fun toMenuProduct(menuProduct: MenuProductEntity): MenuProduct
    fun toMenuProduct(menuProductServer: MenuProductServer): MenuProduct
    fun toMenuProductList(
        menuProductWithCategoryEntityList: List<MenuProductWithCategoryEntity>
    ): List<MenuProduct>
}
