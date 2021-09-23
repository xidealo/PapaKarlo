package com.bunbeauty.domain.model.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderMenuProduct(
    val name: String,
    override val cost: Int,
    override val discountCost: Int?,
    val weight: Int?,
    val description: String,
    val comboDescription: String?,
    val photoLink: String
) : Product(), Parcelable