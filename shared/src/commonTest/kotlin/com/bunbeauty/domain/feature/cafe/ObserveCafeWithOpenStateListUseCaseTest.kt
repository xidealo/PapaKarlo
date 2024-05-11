package com.bunbeauty.domain.feature.cafe

import com.bunbeauty.shared.domain.feature.cafe.GetCafeListUseCase
import com.bunbeauty.shared.domain.feature.cafe.ObserveCafeWithOpenStateListUseCase
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityTimeZoneUseCase
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.model.cafe.CafeOpenState
import com.bunbeauty.shared.domain.model.cafe.CafeWithOpenState
import com.bunbeauty.shared.domain.model.date_time.MinuteSecond
import com.bunbeauty.shared.domain.util.IDateTimeUtil
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ObserveCafeWithOpenStateListUseCaseTest {

    @MockK
    private lateinit var getSelectedCityTimeZoneUseCase: GetSelectedCityTimeZoneUseCase

    @MockK
    private lateinit var dataTimeUtil: IDateTimeUtil

    @MockK
    private lateinit var getCafeListUseCase: GetCafeListUseCase

    @InjectMockKs
    private lateinit var observeCafeWithOpenStateListUseCase: ObserveCafeWithOpenStateListUseCase

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `return closed cafe when time is before opening`() = runTest {
        val timeZone = "GMT+3"
        coEvery { getSelectedCityTimeZoneUseCase() } returns timeZone
        coEvery {
            dataTimeUtil.getCurrentMinuteSecond(timeZone)
        } returns MinuteSecond(
            minuteOfDay = 7 * 60, // 7:00:00
            secondOfMinute = 0,
        )
        val cafe = getFakeCafe(
            fromTime = 8 * 60 * 60, // 8:00:00
            toTime = 20 * 60 * 60, // 20:00:00
        )
        coEvery { getCafeListUseCase() } returns listOf(cafe)
        val expected = listOf(
            CafeWithOpenState(
                cafe = cafe,
                openState = CafeOpenState.Closed
            )
        )

        val result = observeCafeWithOpenStateListUseCase().first()

        assertEquals(expected, result)
    }

    @Test
    fun `return open cafe when time is after opening`() = runTest {
        val timeZone = "GMT+3"
        coEvery { getSelectedCityTimeZoneUseCase() } returns timeZone
        coEvery {
            dataTimeUtil.getCurrentMinuteSecond(timeZone)
        } returns MinuteSecond(
            minuteOfDay = 8 * 60, // 8:00:30
            secondOfMinute = 30,
        )
        val cafe = getFakeCafe(
            fromTime = 8 * 60 * 60, // 8:00:00
            toTime = 20 * 60 * 60, // 20:00:00
        )
        coEvery { getCafeListUseCase() } returns listOf(cafe)
        val expected = listOf(
            CafeWithOpenState(
                cafe = cafe,
                openState = CafeOpenState.Opened
            )
        )

        val result = observeCafeWithOpenStateListUseCase().first()

        assertEquals(expected, result)
    }

    @Test
    fun `return close soon cafe when time is close to closing`() = runTest {
        val timeZone = "GMT+3"
        coEvery { getSelectedCityTimeZoneUseCase() } returns timeZone
        coEvery {
            dataTimeUtil.getCurrentMinuteSecond(timeZone)
        } returns MinuteSecond(
            minuteOfDay = 19 * 60 + 30, // 19:30:00
            secondOfMinute = 0,
        )
        val cafe = getFakeCafe(
            fromTime = 8 * 60 * 60, // 8:00:00
            toTime = 20 * 60 * 60, // 20:00:00
        )
        coEvery { getCafeListUseCase() } returns listOf(cafe)
        val expected = listOf(
            CafeWithOpenState(
                cafe = cafe,
                openState = CafeOpenState.CloseSoon(30)
            )
        )

        val result = observeCafeWithOpenStateListUseCase().first()

        assertEquals(expected, result)
    }

    @Test
    fun `return closed cafe when time is after closing`() = runTest {
        val timeZone = "GMT+3"
        coEvery { getSelectedCityTimeZoneUseCase() } returns timeZone
        coEvery {
            dataTimeUtil.getCurrentMinuteSecond(timeZone)
        } returns MinuteSecond(
            minuteOfDay = 20 * 60 + 1, // 20:01:00
            secondOfMinute = 0,
        )
        val cafe = getFakeCafe(
            fromTime = 8 * 60 * 60, // 8:00:00
            toTime = 20 * 60 * 60, // 20:00:00
        )
        coEvery { getCafeListUseCase() } returns listOf(cafe)
        val expected = listOf(
            CafeWithOpenState(
                cafe = cafe,
                openState = CafeOpenState.Closed
            )
        )

        val result = observeCafeWithOpenStateListUseCase().first()

        assertEquals(expected, result)
    }

    private fun getFakeCafe(fromTime: Int, toTime: Int): Cafe {
        return Cafe(
            uuid = "uuid",
            fromTime = fromTime,
            toTime = toTime,
            phone = "phone",
            address = "address",
            latitude = 0.0,
            longitude = 0.0,
            cityUuid = "cityUuid",
            isVisible = true,
        )
    }
}