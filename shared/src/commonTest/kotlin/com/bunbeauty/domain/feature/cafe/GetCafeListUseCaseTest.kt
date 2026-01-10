package com.bunbeauty.domain.feature.cafe

import com.bunbeauty.core.domain.cafe.GetCafeListUseCaseImpl
import com.bunbeauty.core.domain.exeptions.EmptyCafeListException
import com.bunbeauty.core.domain.repo.CafeRepo
import com.bunbeauty.core.model.cafe.Cafe
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetCafeListUseCaseTest {
    private val cafeRepo: CafeRepo = mock()

    private val getCafeListUseCase: GetCafeListUseCaseImpl =
        GetCafeListUseCaseImpl(
            cafeRepo = cafeRepo,
        )

    @Test
    fun `throw EmptyCafeListException when no visible cafes`() =
        runTest {
            everySuspend {
                cafeRepo.getCafeList()
            } returns
                listOf(
                    getFakeCafe(uuid = "1", isVisible = false),
                )

            assertFailsWith<EmptyCafeListException> {
                getCafeListUseCase()
            }
        }

    @Test
    fun `invoke should return only visible cafes`() =
        runTest {
            val cityUuid = "cityUuid"
            val cafe1 = getFakeCafe(uuid = "1", isVisible = true)
            val cafe2 = getFakeCafe(uuid = "2", isVisible = false)
            val cafe3 = getFakeCafe(uuid = "3", isVisible = true)
            val cafeList = listOf(cafe1, cafe2, cafe3)
            everySuspend { cafeRepo.getCafeList() } returns cafeList
            val expected = listOf(cafe1, cafe3)

            val result = getCafeListUseCase()

            assertEquals(expected, result)
        }

    private fun getFakeCafe(
        uuid: String,
        isVisible: Boolean,
    ): Cafe =
        Cafe(
            uuid = uuid,
            fromTime = 0,
            toTime = 0,
            phone = "phone",
            address = "address",
            latitude = 0.0,
            longitude = 0.0,
            cityUuid = "cityUuid",
            isVisible = isVisible,
            workType = Cafe.WorkType.DELIVERY_AND_PICKUP,
            workload = Cafe.Workload.LOW,
            additionalUtensils = false,
        )
}
