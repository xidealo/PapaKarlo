package com.example.domain_api.mapper

import com.bunbeauty.domain.model.product.MenuProduct
import com.example.domain_api.model.entity.product.MenuProductEntity
import com.example.domain_api.model.server.MenuProductServer

interface IMenuProductMapper {

    fun toEntityModel(menuProduct: MenuProductServer): MenuProductEntity
    fun toModel(menuProduct: MenuProductEntity): MenuProduct
}