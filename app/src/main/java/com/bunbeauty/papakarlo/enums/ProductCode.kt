package com.bunbeauty.papakarlo.enums

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class ProductCode(val code: Int) : Parcelable {
    All(0),
    Pizza(1),
    Hamburger(2),
    Potato(3),
    OnCoals(4)
}