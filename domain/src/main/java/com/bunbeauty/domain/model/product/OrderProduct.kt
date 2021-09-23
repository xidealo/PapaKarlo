package com.bunbeauty.domain.model.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderProduct(
    val uuid: String,
    override val count: Int,
    override val menuProduct: OrderMenuProduct,
) : ProductPosition(), Parcelable
