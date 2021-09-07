package com.bunbeauty.domain.model.address

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class UserAddress(
    val uuid: String = UUID.randomUUID().toString(),
    val street: String,
    val house: String,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
    val streetUuid: String,
    val userUuid: String?,
) : Parcelable
