package com.bunbeauty.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Street(
    val uuid: String,
    val name: String,
    val cityUuid: String,
) : Parcelable
