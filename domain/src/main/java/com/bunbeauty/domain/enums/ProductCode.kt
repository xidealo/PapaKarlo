package com.bunbeauty.domain.enums

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class ProductCode : Parcelable {
    ALL,
    COMBO,
    PIZZA,
    BARBECUE,
    BURGER,
    DRINK,
    POTATO,
    SPICE,
    BAKERY,
    OVEN
}