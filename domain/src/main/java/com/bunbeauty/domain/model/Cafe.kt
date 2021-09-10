package com.bunbeauty.domain.model

import android.os.Parcelable
import com.bunbeauty.domain.model.address.CafeAddress
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

    val visible: Boolean,
    val city: String
) : Parcelable
