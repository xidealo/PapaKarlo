package com.bunbeauty.papakarlo.feature.create_order.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TimeUI(
    val hours: Int,
    val minutes: Int,
) : Parcelable