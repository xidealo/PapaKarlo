package com.bunbeauty.domain.feature.orderavailable

import com.bunbeauty.shared.data.repository.OrderAvailableRepository
import com.bunbeauty.shared.domain.feature.orderavailable.GetOrderAvailableUseCase
import com.bunbeauty.shared.domain.model.order.OrderAvailable
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


class GetIsOrderAvailableUseCaseTest {

    @MockK
    private lateinit var orderAvailableRepository: OrderAvailableRepository

    @InjectMockKs
    private lateinit var getOrderAvailableUseCase: GetOrderAvailableUseCase

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `invoke returns true when order is available`() = runTest {
        // Given
        val orderAvailable = orderAvailableMock.copy(available = true)
        coEvery { orderAvailableRepository.getOrderAvailable() } returns orderAvailable

        // When
        val result = getOrderAvailableUseCase.invoke()

        // Then
        assertEquals(true, result)
    }

    @Test
    fun `invoke returns false when order is not available`() = runTest {
        // Given
        val orderAvailable = orderAvailableMock.copy(available = false)
        coEvery { orderAvailableRepository.getOrderAvailable() } returns orderAvailable

        // When
        val result = getOrderAvailableUseCase.invoke()

        // Then
        assertEquals(false, result)
    }

    @Test
    fun `invoke returns default true when order is null`() = runTest {
        // Given
        coEvery { orderAvailableRepository.getOrderAvailable() } returns null

        // When
        val result = getOrderAvailableUseCase.invoke()

        // Then
        assertEquals(true, result)
    }

    val orderAvailableMock = OrderAvailable(available = true)
}