package com.bunbeauty.domain.feature.cafe

import com.bunbeauty.shared.domain.feature.cafe.GetCafeListUseCase
import com.bunbeauty.shared.domain.feature.cafe.ObserveCafeWithOpenStateListUseCase
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityTimeZoneUseCase
import com.bunbeauty.core.model.cafe.Cafe
import com.bunbeauty.core.model.cafe.CafeOpenState
import com.bunbeauty.core.model.cafe.CafeWithOpenState
import com.bunbeauty.shared.domain.model.date_time.MinuteSecond
import com.bunbeauty.shared.domain.util.DateTimeUtil
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ObserveCafeWithOpenStateListUseCaseTest {
    private val getSelectedCityTimeZoneUseCase: GetSelectedCityTimeZoneUseCase = mock()

    private val dataTimeUtil: DateTimeUtil = mock()

    private val getCafeListUseCase: GetCafeListUseCase = mock()

    private val observeCafeWithOpenStateListUseCase: ObserveCafeWithOpenStateListUseCase =
        ObserveCafeWithOpenStateListUseCase(
            getSelectedCityTimeZoneUseCase = getSelectedCityTimeZoneUseCase,
            dataTimeUtil = dataTimeUtil,
            getCafeListUseCase = getCafeListUseCase,
        )

    @Test
    fun `return closed cafe when time is before opening`() =
        runTest {
            val timeZone = "GMT+3"
            everySuspend { getSelectedCityTimeZoneUseCase() } returns timeZone
            everySuspend {
                dataTimeUtil.getCurrentMinuteSecond(timeZone)
            } returns
                MinuteSecond(
                    minuteOfDay = 7 * 60, // 7:00:00
                    secondOfMinute = 0,
                )
            val cafe =
                getFakeCafe(
                    fromTime = 8 * 60 * 60, // 8:00:00
                    toTime = 20 * 60 * 60, // 20:00:00
                )
            everySuspend { getCafeListUseCase() } returns listOf(cafe)
            val expected =
                listOf(
                    CafeWithOpenState(
                        cafe = cafe,
                        openState = CafeOpenState.Closed,
                    ),
                )

            val result = observeCafeWithOpenStateListUseCase().first()

            assertEquals(expected, result)
        }

    @Test
    fun `return open cafe when time is after opening`() =
        runTest {
            val timeZone = "GMT+3"
            everySuspend { getSelectedCityTimeZoneUseCase() } returns timeZone
            everySuspend {
                dataTimeUtil.getCurrentMinuteSecond(timeZone)
            } returns
                MinuteSecond(
                    minuteOfDay = 8 * 60, // 8:00:30
                    secondOfMinute = 30,
                )
            val cafe =
                getFakeCafe(
                    fromTime = 8 * 60 * 60, // 8:00:00
                    toTime = 20 * 60 * 60, // 20:00:00
                )
            everySuspend { getCafeListUseCase() } returns listOf(cafe)
            val expected =
                listOf(
                    CafeWithOpenState(
                        cafe = cafe,
                        openState = CafeOpenState.Opened,
                    ),
                )

            val result = observeCafeWithOpenStateListUseCase().first()

            assertEquals(expected, result)
        }

    @Test
    fun `return close soon cafe when time is close to closing`() =
        runTest {
            val timeZone = "GMT+3"
            everySuspend { getSelectedCityTimeZoneUseCase() } returns timeZone
            everySuspend {
                dataTimeUtil.getCurrentMinuteSecond(timeZone)
            } returns
                MinuteSecond(
                    minuteOfDay = 19 * 60 + 30, // 19:30:00
                    secondOfMinute = 0,
                )
            val cafe =
                getFakeCafe(
                    fromTime = 8 * 60 * 60, // 8:00:00
                    toTime = 20 * 60 * 60, // 20:00:00
                )
            everySuspend { getCafeListUseCase() } returns listOf(cafe)
            val expected =
                listOf(
                    CafeWithOpenState(
                        cafe = cafe,
                        openState = CafeOpenState.CloseSoon(30),
                    ),
                )

            val result = observeCafeWithOpenStateListUseCase().first()

            assertEquals(expected, result)
        }

    @Test
    fun `return closed cafe when time is after closing`() =
        runTest {
            val timeZone = "GMT+3"
            everySuspend { getSelectedCityTimeZoneUseCase() } returns timeZone
            everySuspend {
                dataTimeUtil.getCurrentMinuteSecond(timeZone)
            } returns
                MinuteSecond(
                    minuteOfDay = 20 * 60 + 1, // 20:01:00
                    secondOfMinute = 0,
                )
            val cafe =
                getFakeCafe(
                    fromTime = 8 * 60 * 60, // 8:00:00
                    toTime = 20 * 60 * 60, // 20:00:00
                )
            everySuspend { getCafeListUseCase() } returns listOf(cafe)
            val expected =
                listOf(
                    CafeWithOpenState(
                        cafe = cafe,
                        openState = CafeOpenState.Closed,
                    ),
                )

            val result = observeCafeWithOpenStateListUseCase().first()

            assertEquals(expected, result)
        }

    private fun getFakeCafe(
        fromTime: Int,
        toTime: Int,
    ): Cafe =
        Cafe(
            uuid = "uuid",
            fromTime = fromTime,
            toTime = toTime,
            phone = "phone",
            address = "address",
            latitude = 0.0,
            longitude = 0.0,
            cityUuid = "cityUuid",
            isVisible = true,
            workload = Cafe.Workload.LOW,
            workType = Cafe.WorkType.DELIVERY_AND_PICKUP,
            additionalUtensils = false,
        )
}
