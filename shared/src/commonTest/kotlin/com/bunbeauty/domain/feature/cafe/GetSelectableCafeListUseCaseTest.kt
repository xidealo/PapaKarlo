package com.bunbeauty.domain.feature.cafe

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.exeptions.EmptyCafeListException
import com.bunbeauty.shared.domain.feature.cafe.GetCafeListUseCase
import com.bunbeauty.shared.domain.feature.cafe.GetSelectableCafeListUseCase
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.model.cafe.SelectableCafe
import com.bunbeauty.shared.domain.repo.CafeRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertFailsWith

class GetSelectableCafeListUseCaseTest {

    private val dataStoreRepo: DataStoreRepo = mock()
    private val cafeRepo: CafeRepo = mock()
    private val getCafeListUseCase: GetCafeListUseCase = mock()

    private val getSelectableCafeListUseCase: GetSelectableCafeListUseCase =
        GetSelectableCafeListUseCase(
            dataStoreRepo = dataStoreRepo,
            cafeRepo = cafeRepo,
            getCafeListUseCase = getCafeListUseCase
        )

    @Test
    fun `return cafe list with selected cafe`() = runTest {
        val selectedCityUuid = "cityUuid"
        val userUuid = "userUuid"
        val selectedCafeUuid = "selectedCafeUuid"
        val selectedCafe = generateCafe(selectedCafeUuid)
        val cafeList = listOf(
            generateCafe("uuid1"),
            generateCafe("uuid2"),
            generateCafe(selectedCafeUuid)
        )
        val expectedResult = listOf(
            generateSelectableCafe("uuid1", false),
            generateSelectableCafe("uuid2", false),
            generateSelectableCafe(selectedCafeUuid, true)
        )

        everySuspend { dataStoreRepo.getSelectedCityUuid() } returns selectedCityUuid
        everySuspend { dataStoreRepo.getUserUuid() } returns userUuid
        everySuspend {
            cafeRepo.getSelectedCafeByUserAndCityUuid(
                userUuid,
                selectedCityUuid
            )
        } returns selectedCafe
        everySuspend { getCafeListUseCase() } returns cafeList

        val result = getSelectableCafeListUseCase()

        assertContentEquals(expectedResult, result)
    }

    @Test
    fun `return cafe list with first selected cafe if cafe is not selected`() = runTest {
        val selectedCityUuid = "cityUuid"
        val userUuid = "userUuid"
        val cafeList = listOf(
            generateCafe("uuid1"),
            generateCafe("uuid2"),
            generateCafe("uuid3")
        )
        val expectedResult = listOf(
            generateSelectableCafe("uuid1", true),
            generateSelectableCafe("uuid2", false),
            generateSelectableCafe("uuid3", false)
        )

        everySuspend { dataStoreRepo.getSelectedCityUuid() } returns selectedCityUuid
        everySuspend { dataStoreRepo.getUserUuid() } returns userUuid
        everySuspend {
            cafeRepo.getSelectedCafeByUserAndCityUuid(
                userUuid,
                selectedCityUuid
            )
        } returns null
        everySuspend { getCafeListUseCase() } returns cafeList

        val result = getSelectableCafeListUseCase()

        assertContentEquals(expectedResult, result)
    }

    @Test
    fun `throw exception if cafe list is empty`() {
        val selectedCityUuid = "cityUuid"
        val userUuid = "userUuid"

        everySuspend { dataStoreRepo.getSelectedCityUuid() } returns selectedCityUuid
        everySuspend { dataStoreRepo.getUserUuid() } returns userUuid
        everySuspend {
            cafeRepo.getSelectedCafeByUserAndCityUuid(
                userUuid,
                selectedCityUuid
            )
        } returns generateCafe(selectedCityUuid)
        everySuspend { getCafeListUseCase() } returns emptyList()

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
        isVisible = true
    )

    private fun generateSelectableCafe(uuid: String, isSelected: Boolean) = SelectableCafe(
        cafe = Cafe(
            uuid = uuid,
            fromTime = 0,
            toTime = 0,
            phone = "phone",
            address = "address",
            latitude = 0.0,
            longitude = 0.0,
            cityUuid = "cityUuid",
            isVisible = true
        ),
        isSelected = isSelected
    )
}
