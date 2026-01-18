package com.bunbeauty.createorder.ui.deferredtime

import androidx.compose.runtime.Composable
import com.bunbeauty.core.model.date_time.Time
import com.bunbeauty.createorder.presentation.createorder.CreateOrder
import com.bunbeauty.createorder.ui.TimeUI
import org.jetbrains.compose.resources.stringResource
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.asap

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
