package com.bunbeauty.domain.feature.splash

import com.bunbeauty.getCity
import com.bunbeauty.shared.domain.feature.splash.CheckOneCityUseCase
import com.bunbeauty.shared.domain.repo.CityRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CheckOneCityUseCaseTest {
    private val cityRepo: CityRepo = mock()
    private val useCase: CheckOneCityUseCase = CheckOneCityUseCase(cityRepo = cityRepo)

    @Test
    fun `invoke returns true when city list has exactly one city`() =
        runTest {
            // Given
            everySuspend { cityRepo.getCityList() } returns
                listOf(
                    getCity(),
                )

            // When
            val result = useCase.invoke()

            // Then
            assertTrue(result)
        }

    @Test
    fun `invoke returns false when city list has more than one city`() =
        runTest {
            // Given
            everySuspend { cityRepo.getCityList() } returns
                listOf(
                    getCity(name = "City1"),
                    getCity(name = "City2"),
                )

            // When
            val result = useCase.invoke()

            // Then
            assertFalse(result)
        }

    @Test
    fun `invoke returns false when city list is empty`() =
        runTest {
            // Given
            everySuspend { cityRepo.getCityList() } returns emptyList()

            // When
            val result = useCase.invoke()

            // Then
            assertFalse(result)
        }
}
