package com.bunbeauty.domain.feature.cafe

import com.bunbeauty.getCafe
import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.exeptions.EmptyCafeListException
import com.bunbeauty.core.domain.exeptions.NoSelectedCityUuidException
import com.bunbeauty.shared.domain.exeptions.NoUserUuidException
import com.bunbeauty.shared.domain.feature.cafe.GetCafeListUseCase
import com.bunbeauty.shared.domain.feature.cafe.GetSelectableCafeListUseCase
import com.bunbeauty.shared.domain.feature.cafe.IsPickupEnabledFromCafeUseCase
import com.bunbeauty.core.model.cafe.SelectableCafe
import com.bunbeauty.shared.domain.repo.CafeRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetSelectableCafeListUseCaseTest {
    private val dataStoreRepo: DataStoreRepo = mock()
    private val cafeRepo: CafeRepo = mock()
    private val getCafeListUseCase: GetCafeListUseCase = mock()
    private var isPickupEnabledFromCafeUseCase: IsPickupEnabledFromCafeUseCase = mock()

    private val getSelectableCafeListUseCase: GetSelectableCafeListUseCase =
        GetSelectableCafeListUseCase(
            dataStoreRepo = dataStoreRepo,
            cafeRepo = cafeRepo,
            getCafeListUseCase = getCafeListUseCase,
            isPickupEnabledFromCafeUseCaseImpl = isPickupEnabledFromCafeUseCase,
        )

    @Test
    fun `invoke should throw NoSelectedCityUuidException when cityUuid is null`() =
        runTest {
            // Arrange
            everySuspend { dataStoreRepo.getSelectedCityUuid() } returns null

            // Act & Assert
            assertFailsWith<NoSelectedCityUuidException> {
                getSelectableCafeListUseCase()
            }
        }

    @Test
    fun `invoke should throw NoUserUuidException when userUuid is null`() =
        runTest {
            // Arrange
            everySuspend { dataStoreRepo.getSelectedCityUuid() } returns "city1"
            everySuspend { dataStoreRepo.getUserUuid() } returns null

            // Act & Assert
            assertFailsWith<NoUserUuidException> {
                getSelectableCafeListUseCase()
            }
        }

    @Test
    fun `invoke should throw EmptyCafeListException when cafe list is empty`() =
        runTest {
            // Arrange
            everySuspend { dataStoreRepo.getSelectedCityUuid() } returns "city1"
            everySuspend { dataStoreRepo.getUserUuid() } returns "user1"
            everySuspend { getCafeListUseCase() } returns emptyList()

            // Act & Assert
            assertFailsWith<EmptyCafeListException> {
                getSelectableCafeListUseCase()
            }
        }

    @Test
    fun `invoke should return sorted list with selected cafe when user has selected cafe`() =
        runTest {
            // Arrange
            val cityUuid = "city1"
            val userUuid = "user1"
            val cafes =
                listOf(
                    getCafe("cafe1"),
                    getCafe("cafe2"),
                    getCafe("cafe3"),
                )
            val selectedCafe = cafes[1]

            everySuspend { dataStoreRepo.getSelectedCityUuid() } returns cityUuid
            everySuspend { dataStoreRepo.getUserUuid() } returns userUuid
            everySuspend { getCafeListUseCase() } returns cafes
            everySuspend {
                cafeRepo.getSelectedCafeByUserAndCityUuid(
                    userUuid,
                    cityUuid,
                )
            } returns selectedCafe
            everySuspend { isPickupEnabledFromCafeUseCase("cafe1") } returns true
            everySuspend { isPickupEnabledFromCafeUseCase("cafe2") } returns false
            everySuspend { isPickupEnabledFromCafeUseCase("cafe3") } returns true

            val expected =
                listOf(
                    generateSelectableCafe(uuid = cafes[0].uuid, isSelected = false, canBePickup = true),
                    generateSelectableCafe(uuid = cafes[2].uuid, isSelected = false, canBePickup = true),
                    generateSelectableCafe(uuid = cafes[1].uuid, isSelected = true, canBePickup = false),
                )

            // Act
            val result = getSelectableCafeListUseCase()

            // Assert
            assertEquals(expected, result)
        }

    @Test
    fun `invoke should return list with first pickup-enabled cafe selected when no user selection`() =
        runTest {
            // Arrange
            val cityUuid = "city1"
            val userUuid = "user1"
            val cafes =
                listOf(
                    getCafe("cafe1"),
                    getCafe("cafe2"),
                    getCafe("cafe3"),
                )

            everySuspend { dataStoreRepo.getSelectedCityUuid() } returns cityUuid
            everySuspend { dataStoreRepo.getUserUuid() } returns userUuid
            everySuspend { getCafeListUseCase() } returns cafes
            everySuspend {
                cafeRepo.getSelectedCafeByUserAndCityUuid(
                    userUuid,
                    cityUuid,
                )
            } returns null
            everySuspend { isPickupEnabledFromCafeUseCase("cafe1") } returns false
            everySuspend { isPickupEnabledFromCafeUseCase("cafe2") } returns true
            everySuspend { isPickupEnabledFromCafeUseCase("cafe3") } returns true

            val expected =
                listOf(
                    generateSelectableCafe(cafes[1].uuid, isSelected = true, canBePickup = true),
                    generateSelectableCafe(cafes[2].uuid, isSelected = false, canBePickup = true),
                    generateSelectableCafe(cafes[0].uuid, isSelected = false, canBePickup = false),
                )

            // Act
            val result = getSelectableCafeListUseCase()

            // Assert
            assertEquals(expected, result)
        }

    @Test
    fun `invoke should return list with first cafe selected when no pickup-enabled cafes`() =
        runTest {
            // Arrange
            val cityUuid = "city1"
            val userUuid = "user1"
            val cafes =
                listOf(
                    getCafe("cafe1"),
                    getCafe("cafe2"),
                )

            everySuspend { dataStoreRepo.getSelectedCityUuid() } returns cityUuid
            everySuspend { dataStoreRepo.getUserUuid() } returns userUuid
            everySuspend { getCafeListUseCase() } returns cafes
            everySuspend {
                cafeRepo.getSelectedCafeByUserAndCityUuid(
                    userUuid,
                    cityUuid,
                )
            } returns null
            everySuspend { isPickupEnabledFromCafeUseCase("cafe1") } returns false
            everySuspend { isPickupEnabledFromCafeUseCase("cafe2") } returns false

            val expected =
                listOf(
                    generateSelectableCafe(cafes[0].uuid, isSelected = true, canBePickup = false),
                    generateSelectableCafe(cafes[1].uuid, isSelected = false, canBePickup = false),
                )

            // Act
            val result = getSelectableCafeListUseCase()

            // Assert
            assertEquals(expected, result)
        }

    private fun generateSelectableCafe(
        uuid: String,
        isSelected: Boolean,
        canBePickup: Boolean,
    ) = SelectableCafe(
        cafe = getCafe(uuid),
        isSelected = isSelected,
        canBePickup = canBePickup,
    )
}
