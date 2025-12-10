package com.bunbeauty.shared.ui.screen.deferredtime

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.presentation.createorder.CreateOrder
import com.bunbeauty.shared.ui.screen.createorder.TimeUI
import papakarlo.shared.generated.resources.Res
import papakarlo.shared.generated.resources.asap

@Composable
fun CreateOrder.DeferredTime.toDeferredTimeString(): String =
    when (this) {
        is CreateOrder.DeferredTime.Asap -> {
            stringResource(Res.string.asap)
        }

        is CreateOrder.DeferredTime.Later -> {
            "${time.hours.withFirstZero()}:${time.minutes.withFirstZero()}"
        }
    }

fun Time.toTimeUI(): TimeUI =
    TimeUI(
        hours = hours,
        minutes = minutes,
    )

private fun Int.withFirstZero(): String =
    if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
