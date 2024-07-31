package com.bunbeauty.domain.feature.cafe

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.exeptions.EmptyCafeListException
import com.bunbeauty.shared.domain.exeptions.NoSelectedCityUuidException
import com.bunbeauty.shared.domain.feature.cafe.GetCafeListUseCase
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.repo.CafeRepo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetCafeListUseCaseTest {
    @MockK
    private lateinit var dataStoreRepo: DataStoreRepo

    @MockK
    private lateinit var cafeRepo: CafeRepo

    @InjectMockKs
    private lateinit var getCafeListUseCase: GetCafeListUseCase

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `throw NoSelectedCityUuidException when no city is selected`() = runTest {
        coEvery { dataStoreRepo.getSelectedCityUuid() } returns null

        assertFailsWith<NoSelectedCityUuidException> {
            getCafeListUseCase()
        }
    }

    @Test
    fun `throw EmptyCafeListException when no visible cafes`() = runTest {
        val cityUuid = "cityUuid"
        coEvery { dataStoreRepo.getSelectedCityUuid() } returns cityUuid
        coEvery {
            cafeRepo.getCafeList(cityUuid)
        } returns listOf(
            getFakeCafe(uuid = "1", isVisible = false)
        )

        assertFailsWith<EmptyCafeListException> {
            getCafeListUseCase()
        }
    }

    @Test
    fun `invoke should return only visible cafes`() = runTest {
        val cityUuid = "cityUuid"
        val cafe1 = getFakeCafe(uuid = "1", isVisible = true)
        val cafe2 = getFakeCafe(uuid = "2", isVisible = false)
        val cafe3 = getFakeCafe(uuid = "3", isVisible = true)
        val cafeList = listOf(cafe1, cafe2, cafe3)
        coEvery { dataStoreRepo.getSelectedCityUuid() } returns cityUuid
        coEvery { cafeRepo.getCafeList(cityUuid) } returns cafeList
        val expected = listOf(cafe1, cafe3)

        val result = getCafeListUseCase()

        assertEquals(expected, result)
    }

    private fun getFakeCafe(uuid: String, isVisible: Boolean): Cafe {
        return Cafe(
            uuid = uuid,
            fromTime = 0,
            toTime = 0,
            phone = "phone",
            address = "address",
            latitude = 0.0,
            longitude = 0.0,
            cityUuid = "cityUuid",
            isVisible = isVisible
        )
    }
}
