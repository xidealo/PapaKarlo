package com.bunbeauty.data.mapper.firebase

import com.bunbeauty.data.mapper.Mapper
import com.bunbeauty.domain.model.ui.MenuProduct
import com.bunbeauty.domain.model.firebase.MenuProductFirebase
import javax.inject.Inject

class MenuProductMapper @Inject constructor() : Mapper<MenuProductFirebase, MenuProduct> {

    override fun from(model: MenuProductFirebase): MenuProduct {
        return MenuProduct(
            "empty uuid",
            name = model.name,
            cost = model.cost,
            discountCost = model.discountCost,
            weight = model.weight ?: 0,
            description = model.description ?: "",
            comboDescription = model.comboDescription ?: "",
            photoLink = model.photoLink,
            productCode = model.productCode,
            barcode = model.barcode,
        )
    }

    /**
     * Set uuid after convert
     */
    override fun to(model: MenuProduct): MenuProductFirebase {
        return MenuProductFirebase(
            name = model.name,
            cost = model.cost,
            discountCost = model.discountCost,
            weight = model.weight,
            description = model.description,
            comboDescription = checkEmptyString(model.comboDescription),
            photoLink = model.photoLink,
            productCode = model.productCode,
            barcode = model.barcode,
        )
    }
}