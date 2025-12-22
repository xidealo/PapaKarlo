package com.bunbeauty.domain.feature.cafe

import com.bunbeauty.getCafe
import com.bunbeauty.shared.domain.feature.cafe.IsPickupEnabledFromCafeUseCase
import com.bunbeauty.shared.domain.feature.cafe.IsPickupEnabledFromCafeUseCaseImpl
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.repo.CafeRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class IsPickupEnabledFromCafeUseCaseTest {
    private val cafeRepo: CafeRepo = mock()

    private val isPickupEnabledFromCafeUseCase: IsPickupEnabledFromCafeUseCase =
        IsPickupEnabledFromCafeUseCaseImpl(
            cafeRepo = cafeRepo,
        )

    @Test
    fun `invoke should return true when workType is PICKUP`() =
        runTest {
            // Arrange
            val cafeUuid = "cafe1"
            everySuspend { cafeRepo.getCafeByUuid(cafeUuid) } returns
                getCafe(
                    uuid = cafeUuid,
                    workType = Cafe.WorkType.PICKUP,
                )

            // Act
            val result = isPickupEnabledFromCafeUseCase(cafeUuid)

            // Assert
            assertEquals(true, result)
        }

    @Test
    fun `invoke should return true when workType is DELIVERY_AND_PICKUP`() =
        runTest {
            // Arrange
            val cafeUuid = "cafe1"
            everySuspend { cafeRepo.getCafeByUuid(cafeUuid) } returns
                getCafe(
                    uuid = cafeUuid,
                    workType = Cafe.WorkType.DELIVERY_AND_PICKUP,
                )

            // Act
            val result = isPickupEnabledFromCafeUseCase(cafeUuid)

            // Assert
            assertEquals(true, result)
        }

    @Test
    fun `invoke should return false when workType is DELIVERY_ONLY`() =
        runTest {
            // Arrange
            val cafeUuid = "cafe1"
            everySuspend { cafeRepo.getCafeByUuid(cafeUuid) } returns
                getCafe(
                    uuid = cafeUuid,
                    workType = Cafe.WorkType.DELIVERY,
                )

            // Act
            val result = isPickupEnabledFromCafeUseCase(cafeUuid)

            // Assert
            assertEquals(false, result)
        }

    @Test
    fun `invoke should return false when workType is CLOSED`() =
        runTest {
            // Arrange
            val cafeUuid = "cafe1"
            everySuspend { cafeRepo.getCafeByUuid(cafeUuid) } returns
                getCafe(
                    uuid = cafeUuid,
                    workType = Cafe.WorkType.CLOSED,
                )

            // Act
            val result = isPickupEnabledFromCafeUseCase(cafeUuid)

            // Assert
            assertEquals(false, result)
        }

    @Test
    fun `invoke should return false when cafe is not found`() =
        runTest {
            // Arrange
            val cafeUuid = "non-existent-cafe"
            everySuspend { cafeRepo.getCafeByUuid(cafeUuid) } returns null

            // Act
            val result = isPickupEnabledFromCafeUseCase(cafeUuid)

            // Assert
            assertEquals(false, result)
        }
}
