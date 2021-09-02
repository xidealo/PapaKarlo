package com.bunbeauty.domain.model.ui.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderProduct(
    override val uuid: String,
    override val count: Int,
    override val menuProduct: MenuProduct
) : ProductPosition(), Parcelable
