package com.bunbeauty.domain.model.date_time

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Time(
    val hourOfDay: Int,
    val minuteOfHour: Int,
) : Parcelable
