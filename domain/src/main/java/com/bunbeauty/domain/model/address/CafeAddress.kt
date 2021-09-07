package com.bunbeauty.domain.model.address

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CafeAddress(
    val city: String,
    val street: String,
    val house: String,
    val comment: String?,
    val cafeUuid: String,
) : Parcelable