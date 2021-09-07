package com.bunbeauty.data_firebase.mapper

import com.bunbeauty.domain.model.product.MenuProduct
import com.example.domain_firebase.mapper.IMenuProductMapper
import com.example.domain_firebase.model.entity.product.MenuProductEntity
import com.example.domain_firebase.model.firebase.MenuProductFirebase
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

//    override fun toEntityModel(menuProduct: MenuProductServer): MenuProductEntity {
//        return MenuProductEntity(
//            uuid = menuProduct.uuid,
//            name = menuProduct.name,
//            cost = menuProduct.cost,
//            discountCost = menuProduct.discountCost,
//            weight = menuProduct.weight,
//            description = menuProduct.description,
//            comboDescription = menuProduct.comboDescription,
//            photoLink = menuProduct.photoLink,
//            productCode = menuProduct.productCode,
//            barcode = menuProduct.barcode,
//            visible = menuProduct.visible,
//        )
//    }

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