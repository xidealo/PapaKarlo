package com.bunbeauty.data.mapper

import com.bunbeauty.domain.mapper.IMenuProductMapper
import com.bunbeauty.domain.model.firebase.MenuProductFirebase
import com.bunbeauty.domain.model.entity.product.MenuProductEntity
import com.bunbeauty.domain.model.ktor.MenuProductServer
import com.bunbeauty.domain.model.ui.product.MenuProduct
import java.util.*
import javax.inject.Inject

class MenuProductMapper @Inject constructor() : IMenuProductMapper {

    override fun toFirebaseModel(menuProduct: MenuProduct): MenuProductFirebase {
        return MenuProductFirebase(
            name = menuProduct.name,
            cost = menuProduct.cost,
            discountCost = menuProduct.discountCost,
            weight = menuProduct.weight,
            description = menuProduct.description,
            comboDescription = menuProduct.comboDescription,
            photoLink = menuProduct.photoLink,
            productCode = menuProduct.productCode,
            barcode = null,
            visible = true,
        )
    }

    override fun toEntityModel(uuid: String, menuProduct: MenuProductFirebase): MenuProductEntity {
        return MenuProductEntity(
            uuid = uuid,
            name = menuProduct.name,
            cost = menuProduct.cost,
            discountCost = menuProduct.discountCost,
            weight = menuProduct.weight,
            description = menuProduct.description,
            comboDescription = menuProduct.comboDescription,
            photoLink = menuProduct.photoLink,
            productCode = menuProduct.productCode,
            barcode = menuProduct.barcode,
            visible = menuProduct.visible,
        )
    }

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
            productCode = menuProduct.productCode,
            barcode = menuProduct.barcode,
            visible = menuProduct.visible,
        )
    }

    override fun toUIModel(menuProduct: MenuProductEntity): MenuProduct {
        return MenuProduct(
            uuid = menuProduct.uuid,
            name = menuProduct.name,
            cost = menuProduct.cost,
            discountCost = menuProduct.discountCost,
            weight = menuProduct.weight,
            description = menuProduct.description,
            comboDescription = menuProduct.comboDescription,
            photoLink = menuProduct.photoLink,
            productCode = menuProduct.productCode
        )
    }
}