package com.bunbeauty.papakarlo.feature.city.screen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityUI(
    val uuid: String,
    val name: String
) : Parcelable
