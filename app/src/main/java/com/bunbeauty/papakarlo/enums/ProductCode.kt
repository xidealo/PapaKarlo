package com.bunbeauty.papakarlo.enums

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class ProductCode : Parcelable {
    All,
    Pizza,
    Hamburger,
    Potato,
    OnCoals
}