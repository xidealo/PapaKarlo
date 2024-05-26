package com.bunbeauty.papakarlo.feature.deferredtime

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.TimeUI
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.presentation.createorder.CreateOrder

@Composable
fun CreateOrder.DeferredTime.toDeferredTimeString(): String {
    return when (this) {
        is CreateOrder.DeferredTime.Asap -> {
            stringResource(R.string.asap)
        }

        is CreateOrder.DeferredTime.Later -> {
            stringResource(R.string.asap)
            "${time.hours.withFirstZero()}:${time.minutes.withFirstZero()}"
        }
    }
}

fun Time.toTimeUI(): TimeUI {
    return TimeUI(
        hours = hours,
        minutes = minutes,
    )
}

private fun Int.withFirstZero(): String {
    return if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
}