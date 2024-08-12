package com.bunbeauty.domain.feature.orderavailable

import com.bunbeauty.shared.domain.feature.orderavailable.IsOrderAvailableUseCase
import com.bunbeauty.shared.domain.model.order.OrderAvailability
import com.bunbeauty.shared.domain.repo.OrderAvailableRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GetIsOrderAvailableUseCaseTest {

    private val orderAvailableRepository: OrderAvailableRepo = mock()

    private var isOrderAvailableUseCase: IsOrderAvailableUseCase = IsOrderAvailableUseCase(
        orderAvailableRepository = orderAvailableRepository
    )

    @Test
    fun `invoke returns true when order is available`() = runTest {
        // Given
        val orderAvailable = orderAvailabilityMock.copy(available = true)
        everySuspend { orderAvailableRepository.getOrderAvailable() } returns orderAvailable

        // When
        val result = isOrderAvailableUseCase.invoke()

        // Then
        assertTrue(result)
    }

    @Test
    fun `invoke returns false when order is not available`() = runTest {
        // Given
        val orderAvailable = orderAvailabilityMock.copy(available = false)
        everySuspend { orderAvailableRepository.getOrderAvailable() } returns orderAvailable

        // When
        val result = isOrderAvailableUseCase.invoke()

        // Then
        assertFalse(result)
    }

    @Test
    fun `invoke returns default true when order is null`() = runTest {
        // Given
        everySuspend { orderAvailableRepository.getOrderAvailable() } returns null

        // When
        val result = isOrderAvailableUseCase.invoke()

        // Then
        assertTrue(result)
    }

    val orderAvailabilityMock = OrderAvailability(available = true)
}
