package com.example.data_api.mapper

import com.bunbeauty.domain.model.product.MenuProduct
import com.example.domain_api.mapper.IMenuProductMapper
import com.example.domain_api.model.entity.product.MenuProductEntity
import com.example.domain_api.model.server.MenuProductServer
import javax.inject.Inject

class MenuProductMapper @Inject constructor() : IMenuProductMapper {

    override fun toEntityModel(menuProduct: MenuProductServer): MenuProductEntity {
        return MenuProductEntity(
            uuid = menuProduct.uuid,
            name = menuProduct.name,
            cost = menuProduct.cost,
            discountCost = menuProduct.discountCost,
            weight = menuProduct.weight,
            description = menuProduct.description,
            comboDescription = menuProduct.comboDescription,
            photoLink = menuProduct.photoLink,
            productCode = menuProduct.productCodes.firstOrNull()?.name ?: "",
            barcode = menuProduct.barcode,
            visible = menuProduct.visible,
        )
    }

    override fun toModel(menuProduct: MenuProductEntity): MenuProduct {
        return MenuProduct(
            uuid = menuProduct.uuid,
            name = menuProduct.name,
            cost = menuProduct.cost,
            discountCost = menuProduct.discountCost,
            weight = menuProduct.weight,
            description = menuProduct.description,
            comboDescription = menuProduct.comboDescription,
            photoLink = menuProduct.photoLink,
            productCode = menuProduct.productCode,
        )
    }
}