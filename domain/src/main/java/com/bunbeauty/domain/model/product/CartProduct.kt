package com.bunbeauty.domain.model.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartProduct(
    val uuid: String,
    override val count: Int,
    override val product: MenuProduct
) : ProductPosition(), Parcelable
