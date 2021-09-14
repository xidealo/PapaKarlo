package com.bunbeauty.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Street(
    var uuid: String,
    var name: String
) : Parcelable
