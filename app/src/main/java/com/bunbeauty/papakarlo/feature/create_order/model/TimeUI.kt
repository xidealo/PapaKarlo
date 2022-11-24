package com.bunbeauty.papakarlo.feature.create_order.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface TimeUI : Parcelable {

    @Parcelize
    class Time(
        val hours: Int,
        val minutes: Int
    ) : TimeUI

    @Parcelize
    object ASAP : TimeUI
}
