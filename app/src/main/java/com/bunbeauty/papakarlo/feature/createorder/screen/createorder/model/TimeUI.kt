package com.bunbeauty.papakarlo.feature.createorder.screen.createorder.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface TimeUI: Parcelable {

    @Parcelize
    class Time(
        val hours: Int,
        val minutes: Int
    ) : TimeUI

    @Parcelize
    data object ASAP : TimeUI

}
