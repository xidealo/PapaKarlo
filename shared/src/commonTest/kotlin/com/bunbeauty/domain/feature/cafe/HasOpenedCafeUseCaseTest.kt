package com.bunbeauty.domain.feature.cafe

import com.bunbeauty.getCafe
import com.bunbeauty.shared.domain.feature.cafe.HasOpenedCafeUseCase
import com.bunbeauty.shared.domain.feature.cafe.IsPickupEnabledFromCafeUseCase
import com.bunbeauty.shared.domain.model.cafe.Cafe
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class HasOpenedCafeUseCaseTest {

    private val isPickupEnabledFromCafeUseCase: IsPickupEnabledFromCafeUseCase = mock()

    private val hasOpenedCafeUseCase: HasOpenedCafeUseCase =
        HasOpenedCafeUseCase(
            isPickupEnabledFromCafeUseCase = isPickupEnabledFromCafeUseCase
        )

    @Test
    fun `invoke should return true when at least one cafe has pickup enabled`() = runTest {
        // Arrange
        val cafes = listOf(
            getCafe("cafe1"),
            getCafe("cafe2")
        )

        everySuspend { isPickupEnabledFromCafeUseCase("cafe1") } returns false
        everySuspend { isPickupEnabledFromCafeUseCase("cafe2") } returns true

        // Act
        val result = hasOpenedCafeUseCase(cafes)

        // Assert
        assertEquals(true, result)
    }

    @Test
    fun `invoke should return false when no cafes have pickup enabled`() = runTest {
        // Arrange
        val cafes = listOf(
            getCafe("cafe1"),
            getCafe("cafe2")
        )

        everySuspend { isPickupEnabledFromCafeUseCase("cafe1") } returns false
        everySuspend { isPickupEnabledFromCafeUseCase("cafe2") } returns false

        // Act
        val result = hasOpenedCafeUseCase(cafes)

        // Assert
        assertEquals(false, result)
    }

    @Test
    fun `invoke should return false when cafe list is empty`() = runTest {
        // Arrange
        val cafes = emptyList<Cafe>()

        // Act
        val result = hasOpenedCafeUseCase(cafes)

        // Assert
        assertEquals(false, result)
    }

    @Test
    fun `invoke should check all cafes until finding first with pickup enabled`() = runTest {
        // Arrange
        val cafes = listOf(
            getCafe("cafe1"),
            getCafe("cafe2"),
            getCafe("cafe3")
        )

        everySuspend { isPickupEnabledFromCafeUseCase("cafe1") } returns false
        everySuspend { isPickupEnabledFromCafeUseCase("cafe2") } returns true
        // cafe3 should not be checked because we found a match at cafe2

        // Act
        val result = hasOpenedCafeUseCase(cafes)

        // Assert
        assertEquals(true, result)
    }

}