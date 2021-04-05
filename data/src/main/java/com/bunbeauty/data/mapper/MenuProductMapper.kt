package com.bunbeauty.data.mapper

import com.bunbeauty.common.Mapper
import com.bunbeauty.data.model.MenuProduct
import com.bunbeauty.data.model.firebase.MenuProductFirebase
import javax.inject.Inject

class MenuProductMapper @Inject constructor() : Mapper<MenuProductFirebase, MenuProduct> {

    override fun from(e: MenuProduct): MenuProductFirebase {
        return MenuProductFirebase(
            e.name,
            e.cost,
            e.discountCost,
            e.weight,
            e.description,
            checkEmptyString(e.comboDescription),
            e.photoLink,
            e.onFire,
            e.inOven,
            e.productCode,
            e.barcode,
            e.visible
        )
    }

    /**
     * Set uuid after convert
     */
    override fun to(t: MenuProductFirebase): MenuProduct {
        return MenuProduct(
            "empty uuid",
            t.name,
            t.cost,
            t.discountCost,
            t.weight,
            t.description,
            t.comboDescription ?: "",
            t.photoLink,
            t.onFire,
            t.inOven,
            t.productCode,
            t.barcode,
            t.visible
        )
    }
}