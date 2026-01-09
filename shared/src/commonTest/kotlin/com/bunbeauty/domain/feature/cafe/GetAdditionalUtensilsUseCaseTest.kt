package com.bunbeauty.domain.feature.cafe

import com.bunbeauty.getCafe
import com.bunbeauty.shared.domain.feature.cafe.GetAdditionalUtensilsUseCase
import com.bunbeauty.core.domain.repo.CafeRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetAdditionalUtensilsUseCaseTest {
    private val cafeRepo: CafeRepo = mock()

    private val getAdditionalUtensilsUseCase: GetAdditionalUtensilsUseCase =
        GetAdditionalUtensilsUseCase(
            cafeRepo = cafeRepo,
        )

    @Test
    fun `invoke should return true when cafe has additional utensils`() =
        runTest {
            // Arrange
            val cafeUuid = "cafe123"
            everySuspend { cafeRepo.getCafeByUuid(cafeUuid) } returns
                getCafe(
                    uuid = cafeUuid,
                    additionalUtensils = true,
                )

            // Act
            val result = getAdditionalUtensilsUseCase(cafeUuid)

            // Assert
            assertEquals(true, result)
        }

    @Test
    fun `invoke should return false when cafe does not have additional utensils`() =
        runTest {
            // Arrange
            val cafeUuid = "cafe123"
            everySuspend { cafeRepo.getCafeByUuid(cafeUuid) } returns getCafe(uuid = "1")

            // Act
            val result = getAdditionalUtensilsUseCase(cafeUuid)

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
            val result = getAdditionalUtensilsUseCase(cafeUuid)

            // Assert
            assertEquals(false, result)
        }
}
