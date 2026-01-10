package com.bunbeauty.domain.feature.cafe

import com.bunbeauty.core.domain.cafe.GetWorkloadCafeUseCase
import com.bunbeauty.getCafe
import com.bunbeauty.core.model.cafe.Cafe
import com.bunbeauty.core.domain.repo.CafeRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetWorkloadCafeUseCaseTest {
    private val cafeRepo: CafeRepo = mock()

    private val useCase: GetWorkloadCafeUseCase =
        GetWorkloadCafeUseCase(
            cafeRepo = cafeRepo,
        )

    @Test
    fun `invoke should return HIGH workload when cafe has HIGH workload`() =
        runTest {
            // Arrange
            val cafeUuid = "cafe1"
            everySuspend { cafeRepo.getCafeByUuid(cafeUuid) } returns
                getCafe(
                    uuid = cafeUuid,
                    workload = Cafe.Workload.HIGH,
                )

            // Act
            val result = useCase(cafeUuid)

            // Assert
            assertEquals(Cafe.Workload.HIGH, result)
        }

    @Test
    fun `invoke should return AVERAGE workload when cafe has AVERAGE workload`() =
        runTest {
            // Arrange
            val cafeUuid = "cafe1"
            everySuspend { cafeRepo.getCafeByUuid(cafeUuid) } returns
                getCafe(
                    uuid = cafeUuid,
                    workload = Cafe.Workload.AVERAGE,
                )

            // Act
            val result = useCase(cafeUuid)

            // Assert
            assertEquals(Cafe.Workload.AVERAGE, result)
        }

    @Test
    fun `invoke should return LOW workload when cafe has LOW workload`() =
        runTest {
            // Arrange
            val cafeUuid = "cafe1"
            everySuspend { cafeRepo.getCafeByUuid(cafeUuid) } returns
                getCafe(
                    uuid = cafeUuid,
                    workload = Cafe.Workload.LOW,
                )

            // Act
            val result = useCase(cafeUuid)

            // Assert
            assertEquals(Cafe.Workload.LOW, result)
        }

    @Test
    fun `invoke should return LOW workload when cafe is not found`() =
        runTest {
            // Arrange
            val cafeUuid = "non-existent-cafe"
            everySuspend { cafeRepo.getCafeByUuid(cafeUuid) } returns null

            // Act
            val result = useCase(cafeUuid)

            // Assert
            assertEquals(Cafe.Workload.LOW, result)
        }
}
