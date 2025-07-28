package com.bunbeauty.papakarlo.util.string

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.bunbeauty.papakarlo.R
import com.bunbeauty.shared.domain.model.date_time.DateTime
import com.bunbeauty.shared.domain.model.date_time.Time

@Composable
fun DateTime.getDateTimeString(): String {
    val monthName = when (date.monthNumber) {
        1 -> R.string.month_january
        2 -> R.string.month_february
        3 -> R.string.month_march
        4 -> R.string.month_april
        5 -> R.string.month_may
        6 -> R.string.month_june
        7 -> R.string.month_july
        8 -> R.string.month_august
        9 -> R.string.month_september
        10 -> R.string.month_october
        11 -> R.string.month_november
        12 -> R.string.month_december
        else -> R.string.month_unknown
    }.let { monthResourceId ->
        stringResource(monthResourceId)
    }
    return "${date.dayOfMonth} $monthName ${time.getTimeString()}"
}

@Composable
fun Time?.getTimeString(): String {
    return if (this == null) {
        stringResource(R.string.asap)
    } else {
        "${addFirstZero(hours)}:${addFirstZero(minutes)}"
    }
}

private fun addFirstZero(number: Int): String {
    return if (number < 10) {
        "0$number"
    } else {
        number.toString()
    }
}
