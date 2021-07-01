package com.bunbeauty.data.mapper.adapter

import com.bunbeauty.common.Mapper
import com.bunbeauty.domain.model.adapter.CartProductAdapterModel
import com.bunbeauty.domain.model.adapter.MenuProductAdapterModel
import com.bunbeauty.domain.model.local.CartProduct
import com.bunbeauty.domain.model.local.MenuProduct
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.string_helper.IStringHelper
import javax.inject.Inject

class CartProductAdapterMapper @Inject constructor(
    private val productHelper: IProductHelper
) : Mapper<CartProductAdapterModel, CartProduct> {

    override fun from(e: CartProduct): CartProductAdapterModel {
        return CartProductAdapterModel(
            uuid = e.uuid,
            name = e.menuProduct.name,
            cost = productHelper.getCartProductPriceString(e),
            discountCost = productHelper.getCartProductOldPriceString(e),
            photoLink = e.menuProduct.photoLink,
            count = e.count.toString()
        )
    }

    /**
     * Set uuid after convert
     */
    override fun to(t: CartProductAdapterModel): CartProduct {
        return CartProduct()
    }
}