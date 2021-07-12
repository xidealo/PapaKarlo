package com.bunbeauty.domain.util.cafe

import com.bunbeauty.common.Constants.MINUTES_IN_HOUR
import com.bunbeauty.domain.R
import com.bunbeauty.domain.util.resources.IResourcesProvider
import org.joda.time.DateTime
import javax.inject.Inject

class CafeUtil @Inject constructor(
    private val resourcesProvider: IResourcesProvider,
) : ICafeUtil {

    // Рассчитано на то, что кафе заканчивает работать до 24 ночи
    override fun getIsClosedMessage(fromTime: String, toTime: String, now: DateTime): String {
        val beforeStart = getMinutesFromNowToTime(fromTime, now)
        val beforeEnd = getMinutesFromNowToTime(toTime, now)

        return when {
            (beforeStart >= 60) -> {
                "Закрыто. Откроется через ${beforeStart / 60} ч ${beforeStart % 60} мин"
            }
            (beforeStart in 1 until 60) -> {
                "Закрыто. Откроется через ${beforeStart % 60} мин"
            }
            (beforeEnd >= 60) -> {
                "Открыто. Закроется через ${beforeEnd / 60} ч ${beforeEnd % 60} мин"
            }
            (beforeEnd in 1 until 60) -> {
                "Открыто. Закроется через ${beforeEnd % 60} мин"
            }
            else -> {
                "Закрыто. Откроется завтра"
            }
        }
    }

    override fun getIsClosedColor(fromTime: String, toTime: String, now: DateTime): Int {
        val beforeStart = getMinutesFromNowToTime(fromTime, now)
        val beforeEnd = getMinutesFromNowToTime(toTime, now)

        return if (beforeStart < 0 && beforeEnd > 0) {
            resourcesProvider.getColor(R.color.light_green)
        } else {
            resourcesProvider.getColor(R.color.light_red)
        }
    }

    private fun getMinutesFromNowToTime(time: String, now: DateTime): Int {
        val hoursMinutes = time
            .split(Regex("\\W"))
            .map { timePart -> timePart.toInt() }
        val minutes = hoursMinutes[0] * MINUTES_IN_HOUR + hoursMinutes[1]

        val nowMinutes = now.hourOfDay * MINUTES_IN_HOUR + now.minuteOfHour
        return minutes - nowMinutes
    }
}