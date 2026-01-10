package com.bunbeauty.domain.feature.splash

import com.bunbeauty.core.domain.exeptions.NoCityException
import com.bunbeauty.core.domain.repo.CityRepo
import com.bunbeauty.core.domain.splash.SaveOneCityUseCase
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

class SaveOneCityUseCaseTest {
    private val cityRepo: CityRepo = mock()
    private val useCase: SaveOneCityUseCase =
        SaveOneCityUseCase(
            cityRepo = cityRepo,
        )

    @Test
    fun `invoke throws NoTokenException when city list is empty`() =
        runTest {
            // Given
            everySuspend { cityRepo.getCityList() } returns emptyList()

            // When & Then
            assertFailsWith<NoCityException> { useCase.invoke() }
        }
}
