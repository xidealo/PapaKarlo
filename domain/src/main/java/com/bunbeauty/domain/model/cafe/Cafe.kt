package com.bunbeauty.domain.model.cafe

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cafe(
    val uuid: String,
    val fromTime: String,
    val toTime: String,
    val phone: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val cityUuid: String
) : Parcelable
