package com.bunbeauty.domain.feature.splash

import com.bunbeauty.getCity
import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.exeptions.NoTokenException
import com.bunbeauty.shared.domain.feature.splash.SaveOneCityUseCase
import com.bunbeauty.shared.domain.repo.CityRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

class SaveOneCityUseCaseTest {

    private val cityRepo: CityRepo = mock()
    private val dataStoreRepo: DataStoreRepo = mock()
    private val useCase: SaveOneCityUseCase = SaveOneCityUseCase(
        cityRepo = cityRepo,
        dataStoreRepo = dataStoreRepo
    )

    @Test
    fun `invoke saves city UUID when city list has one city`() = runTest {
        // Given
        val mockCity = getCity(uuid = "city-uuid")
        everySuspend { cityRepo.getCityList() } returns listOf(mockCity)
        everySuspend { dataStoreRepo.saveSelectedCityUuid("city-uuid") } returns Unit

        // When
        useCase.invoke()

        // Then
        verifySuspend { dataStoreRepo.saveSelectedCityUuid("city-uuid") }
    }

    @Test
    fun `invoke throws NoTokenException when city list is empty`() = runTest {
        // Given
        everySuspend { cityRepo.getCityList() } returns emptyList()

        // When & Then
        assertFailsWith<NoTokenException> { useCase.invoke() }
    }
}
