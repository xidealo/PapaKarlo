package com.bunbeauty.domain.model.cart

import android.os.Parcelable
import com.bunbeauty.domain.model.product.MenuProduct
import com.bunbeauty.domain.model.product.ProductPosition
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartProduct(
    val uuid: String,
    override val count: Int,
    override val product: MenuProduct
) : ProductPosition(), Parcelable
