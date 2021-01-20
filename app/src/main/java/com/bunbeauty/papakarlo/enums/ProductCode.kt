package com.bunbeauty.papakarlo.enums

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class ProductCode : Parcelable {
    ALL,
    PIZZA,
    BURGER,
    POTATO,
    BARBECUE,
    OVEN
}