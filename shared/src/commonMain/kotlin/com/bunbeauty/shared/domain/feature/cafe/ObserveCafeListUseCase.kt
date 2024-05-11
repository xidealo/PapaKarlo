package com.bunbeauty.shared.domain.feature.cafe

import com.bunbeauty.shared.domain.feature.city.GetSelectedCityTimeZoneUseCase
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.util.IDateTimeUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ObserveCafeListUseCase(
    private val getSelectedCityTimeZoneUseCase: GetSelectedCityTimeZoneUseCase,
    private val dataTimeUtil: IDateTimeUtil,
    private val getCafeListUseCase: GetCafeListUseCase,
) {

    suspend operator fun invoke(): Flow<List<Cafe>> {
        val timeZone = getSelectedCityTimeZoneUseCase()
        return observeMinutesOfDay(timeZone).map {
            getCafeListUseCase()
        }
    }

    private fun observeMinutesOfDay(timeZone: String): Flow<Int> = flow {
        while (true) {
            val currentMinuteSecond = dataTimeUtil.getCurrentMinuteSecond(timeZone)
            emit(currentMinuteSecond.minuteOfDay)
            delay((60 - currentMinuteSecond.secondOfMinute) * 1_000L)
        }
    }

}