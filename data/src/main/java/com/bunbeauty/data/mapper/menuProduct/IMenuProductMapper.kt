package com.bunbeauty.data.mapper.menuProduct

import com.bunbeauty.data.database.entity.product.MenuProductEntity
import com.bunbeauty.data.database.entity.product_with_category.MenuProductWithCategory
import com.bunbeauty.data.network.model.MenuProductServer
import com.bunbeauty.domain.model.product.MenuProduct

interface IMenuProductMapper {

    fun toEntityModel(menuProduct: MenuProductServer): MenuProductWithCategory
    fun toModel(menuProduct: MenuProductEntity): MenuProduct
    fun toModel(menuProduct: MenuProductWithCategory): MenuProduct
}