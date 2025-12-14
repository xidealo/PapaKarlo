package com.bunbeauty.shared.domain.feature.order

import com.bunbeauty.shared.domain.model.date_time.Time
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

//Щас не работае если пограничное время тк думает что текущий день
class GetIsTimeAvailableUseCase {

    @OptIn(ExperimentalTime::class)
    operator fun invoke(time: Time): Boolean {

        val timeZone = TimeZone.currentSystemDefault()
        val currentInstant = Clock.System.now()

        val localDateTime = currentInstant.toLocalDateTime(timeZone)
        val selectedInstant = LocalDateTime(
            localDateTime.year,
            localDateTime.month,
            day = localDateTime.day,
            hour = time.hours,
            minute = time.minutes
        ).toInstant(timeZone)

        return selectedInstant >= currentInstant.plus(1, DateTimeUnit.HOUR)
    }
}