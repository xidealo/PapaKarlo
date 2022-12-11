package com.bunbeauty.shared.presentation.create_order.model

import com.bunbeauty.shared.SerializableMultiplatform

sealed interface TimeUI : SerializableMultiplatform {

    class Time(
        val hours: Int,
        val minutes: Int
    ) : TimeUI

    object ASAP : TimeUI
}
