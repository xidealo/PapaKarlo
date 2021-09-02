package com.bunbeauty.domain.model.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Street(
    var uuid: String,
    var name: String,
    var districtUuid: String
) : Parcelable
