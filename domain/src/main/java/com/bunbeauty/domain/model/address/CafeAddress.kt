package com.bunbeauty.domain.model.address

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CafeAddress(
    val address: String,
    val cafeUuid: String,
) : Parcelable