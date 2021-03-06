package com.bunbeauty.domain.model.local.cafe

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coordinate(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
): Parcelable
