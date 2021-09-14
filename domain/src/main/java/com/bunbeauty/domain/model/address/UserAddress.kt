package com.bunbeauty.domain.model.address

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserAddress(
    val uuid: String = "",
    val street: String,
    val house: String,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
    val streetUuid: String,
    val userUuid: String?,
) : Parcelable
