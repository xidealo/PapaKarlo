package com.bunbeauty.domain.feature.cafe

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.exeptions.EmptyCafeListException
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.model.cafe.SelectableCafe
import com.bunbeauty.shared.domain.repo.CafeRepo
import com.bunbeauty.shared.domain.use_case.cafe.GetSelectableCafeListUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertFailsWith

class GetSelectableCafeListUseCaseTest {

    private val dataStoreRepo: DataStoreRepo = mockk()
    private val cafeRepo: CafeRepo = mockk()
    private lateinit var getSelectableCafeListUseCase: GetSelectableCafeListUseCase

    @BeforeTest
    fun setup() {
        getSelectableCafeListUseCase = GetSelectableCafeListUseCase(
            dataStoreRepo = dataStoreRepo,
            cafeRepo = cafeRepo,
        )
    }

    @Test
    fun `return cafe list with selected cafe`() = runTest {
        // Arrange
        val selectedCityUuid = "cityUuid"
        val userUuid = "userUuid"
        val selectedCafeUuid = "selectedCafeUuid"
        val selectedCafe = generateCafe(selectedCafeUuid)
        val cafeList = listOf(
            generateCafe("uuid1"),
            generateCafe("uuid2"),
            generateCafe(selectedCafeUuid),
        )
        val expectedResult = listOf(
            generateSelectableCafe("uuid1", false),
            generateSelectableCafe("uuid2", false),
            generateSelectableCafe(selectedCafeUuid, true),
        )

        coEvery { dataStoreRepo.getSelectedCityUuid() } returns selectedCityUuid
        coEvery { dataStoreRepo.getUserUuid() } returns userUuid
        coEvery {
            cafeRepo.getSelectedCafeByUserAndCityUuid(
                userUuid,
                selectedCityUuid
            )
        } returns selectedCafe
        coEvery { cafeRepo.getCafeList(selectedCityUuid) } returns cafeList

        // Act
        val result = getSelectableCafeListUseCase()

        // Assert
        assertContentEquals(expectedResult, result)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `return cafe list with first selected cafe if cafe is not selected`() = runTest {
        // Arrange
        val selectedCityUuid = "cityUuid"
        val userUuid = "userUuid"
        val cafeList = listOf(
            generateCafe("uuid1"),
            generateCafe("uuid2"),
            generateCafe("uuid3"),
        )
        val expectedResult = listOf(
            generateSelectableCafe("uuid1", true),
            generateSelectableCafe("uuid2", false),
            generateSelectableCafe("uuid3", false),
        )

        coEvery { dataStoreRepo.getSelectedCityUuid() } returns selectedCityUuid
        coEvery { dataStoreRepo.getUserUuid() } returns userUuid
        coEvery {
            cafeRepo.getSelectedCafeByUserAndCityUuid(
                userUuid,
                selectedCityUuid
            )
        } returns null
        coEvery { cafeRepo.getFirstCafeCityUuid(selectedCityUuid) } returns generateCafe("uuid1")
        coEvery { cafeRepo.getCafeList(selectedCityUuid) } returns cafeList

        // Act
        val result = getSelectableCafeListUseCase()

        // Assert
        assertContentEquals(expectedResult, result)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `throw exception if cafe list is empty`() {
        // Arrange
        val selectedCityUuid = "cityUuid"
        val userUuid = "userUuid"

        coEvery { dataStoreRepo.getSelectedCityUuid() } returns selectedCityUuid
        coEvery { dataStoreRepo.getUserUuid() } returns userUuid
        coEvery {
            cafeRepo.getSelectedCafeByUserAndCityUuid(
                userUuid,
                selectedCityUuid
            )
        } returns generateCafe(selectedCityUuid)
        coEvery { cafeRepo.getCafeList(selectedCityUuid) } returns emptyList()

        // Act & Assert
        assertFailsWith<EmptyCafeListException> {
            runTest {
                getSelectableCafeListUseCase()
            }
        }
    }

    private fun generateCafe(uuid: String) = Cafe(
        uuid = uuid,
        fromTime = 0,
        toTime = 0,
        phone = "phone",
        address = "address",
        latitude = 0.0,
        longitude = 0.0,
        cityUuid = "cityUuid",
    )

    private fun generateSelectableCafe(uuid: String, isSelected: Boolean) = SelectableCafe(
        uuid = uuid,
        fromTime = 0,
        toTime = 0,
        phone = "phone",
        address = "address",
        latitude = 0.0,
        longitude = 0.0,
        cityUuid = "cityUuid",
        isSelected = isSelected,
    )
}