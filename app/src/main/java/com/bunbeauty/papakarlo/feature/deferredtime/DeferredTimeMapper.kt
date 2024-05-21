package com.bunbeauty.papakarlo.feature.deferredtime

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.bunbeauty.papakarlo.R
import com.bunbeauty.shared.presentation.createorder.CreateOrder

@Composable
fun CreateOrder.DeferredTime.toDeferredTimeString(): String  {
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

private fun Int.withFirstZero(): String {
    return if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
}