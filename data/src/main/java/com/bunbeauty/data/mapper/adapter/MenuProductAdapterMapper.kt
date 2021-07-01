package com.bunbeauty.data.mapper.adapter

import com.bunbeauty.common.Mapper
import com.bunbeauty.domain.model.adapter.MenuProductAdapterModel
import com.bunbeauty.domain.model.local.MenuProduct
import com.bunbeauty.domain.util.product.IProductHelper
import javax.inject.Inject

class MenuProductAdapterMapper @Inject constructor(
    private val productHelper: IProductHelper
) : Mapper<MenuProductAdapterModel, MenuProduct> {

    override fun from(e: MenuProduct): MenuProductAdapterModel {
        return MenuProductAdapterModel(
            uuid = e.uuid,
            name = e.name,
            cost = productHelper.getMenuProductOldPriceString(e),
            discountCost = productHelper.getMenuProductPriceString(e),
            photoLink = e.photoLink
        )
    }

    /**
     * Set uuid after convert
     */
    override fun to(t: MenuProductAdapterModel): MenuProduct {
        return MenuProduct()
    }
}