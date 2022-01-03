package com.example.domain_api.mapper

import com.bunbeauty.domain.model.product.MenuProduct
import com.example.domain_api.model.entity.product.MenuProductEntity
import com.example.domain_api.model.entity.product_with_category.MenuProductWithCategory
import com.example.domain_api.model.server.MenuProductServer

interface IMenuProductMapper {

    fun toEntityModel(menuProduct: MenuProductServer): MenuProductWithCategory
    fun toModel(menuProduct: MenuProductEntity): MenuProduct
    fun toModel(menuProduct: MenuProductWithCategory): MenuProduct
}