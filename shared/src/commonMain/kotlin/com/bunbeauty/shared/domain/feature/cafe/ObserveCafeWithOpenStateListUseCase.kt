package com.bunbeauty.shared.domain.feature.cafe

import com.bunbeauty.shared.Constants.SECONDS_IN_MINUTE
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityTimeZoneUseCase
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.model.cafe.CafeOpenState
import com.bunbeauty.shared.domain.model.cafe.CafeWithOpenState
import com.bunbeauty.shared.domain.util.IDateTimeUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ObserveCafeWithOpenStateListUseCase(
    private val getSelectedCityTimeZoneUseCase: GetSelectedCityTimeZoneUseCase,
    private val dataTimeUtil: IDateTimeUtil,
    private val getCafeListUseCase: GetCafeListUseCase,
) {

    suspend operator fun invoke(): Flow<List<CafeWithOpenState>> {
        val timeZone = getSelectedCityTimeZoneUseCase()
        val cafeList = getCafeListUseCase()
        return observeMinutesOfDay(timeZone).map { minutesOfDay ->
            cafeList.map { cafe ->
                CafeWithOpenState(
                    cafe = cafe,
                    openState = getCafeOpenState(
                        cafe = cafe,
                        minutesOfDay = minutesOfDay
                    )
                )
            }
        }
    }

    private fun getCafeOpenState(cafe: Cafe, minutesOfDay: Int): CafeOpenState {
        val isClosed = isClosed(
            fromTime = cafe.fromTime,
            toTime = cafe.toTime,
            minutesOfDay = minutesOfDay
        )
        return if (isClosed) {
            CafeOpenState.Closed
        } else {
            val isCloseSoon = isCloseSoon(
                toTime = cafe.toTime,
                minutesOfDay = minutesOfDay
            )
            if (isCloseSoon) {
                CafeOpenState.CloseSoon(
                    minutesUntil = getCloseIn(
                        cafe = cafe,
                        minutesOfDay = minutesOfDay
                    ) ?: 0
                )
            } else {
                CafeOpenState.Opened
            }
        }
    }

    private fun isClosed(fromTime: Int, toTime: Int, minutesOfDay: Int): Boolean {
        return (minutesOfDay < fromTime / SECONDS_IN_MINUTE)
            || (minutesOfDay >= toTime / SECONDS_IN_MINUTE)
    }

    private fun isCloseSoon(toTime: Int, minutesOfDay: Int): Boolean {
        val beforeClose = (toTime / SECONDS_IN_MINUTE) - minutesOfDay

        return beforeClose in 1 until 60
    }

    private fun getCloseIn(cafe: Cafe, minutesOfDay: Int): Int? {
        val beforeClose = (cafe.toTime / SECONDS_IN_MINUTE) - minutesOfDay

        return if (beforeClose in 1 until 60) {
            beforeClose % 60
        } else {
            null
        }
    }

    private fun observeMinutesOfDay(timeZone: String): Flow<Int> = flow {
        while (true) {
            val currentMinuteSecond = dataTimeUtil.getCurrentMinuteSecond(timeZone)
            emit(currentMinuteSecond.minuteOfDay)
            delay((SECONDS_IN_MINUTE - currentMinuteSecond.secondOfMinute) * 1_000L)
        }
    }

}