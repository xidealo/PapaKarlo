package com.bunbeauty.data.mapper.firebase

import com.bunbeauty.common.Mapper
import com.bunbeauty.domain.model.local.MenuProduct
import com.bunbeauty.domain.model.firebase.MenuProductFirebase
import javax.inject.Inject

class MenuProductMapper @Inject constructor() : Mapper<MenuProductFirebase, MenuProduct> {

    override fun from(e: MenuProduct): MenuProductFirebase {
        return MenuProductFirebase(
            name = e.name,
            cost = e.cost,
            discountCost = e.discountCost,
            weight = e.weight,
            description = e.description,
            comboDescription = checkEmptyString(e.comboDescription),
            photoLink = e.photoLink,
            productCode = e.productCode,
            barcode = e.barcode,
        )
    }

    /**
     * Set uuid after convert
     */
    override fun to(t: MenuProductFirebase): MenuProduct {
        return MenuProduct(
            "empty uuid",
            name = t.name,
            cost = t.cost,
            discountCost = t.discountCost,
            weight = t.weight,
            description = t.description,
            comboDescription = t.comboDescription ?: "",
            photoLink = t.photoLink,
            productCode = t.productCode,
            barcode = t.barcode,
        )
    }
}