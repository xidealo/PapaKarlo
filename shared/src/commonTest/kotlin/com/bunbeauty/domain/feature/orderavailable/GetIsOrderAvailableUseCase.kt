//package com.bunbeauty.domain.feature.orderavailable
//
//import com.bunbeauty.shared.data.repository.OrderAvailableRepository
//import com.bunbeauty.shared.domain.feature.orderavailable.IsOrderAvailableUseCase
//import com.bunbeauty.shared.domain.model.order.OrderAvailability
//
//import kotlinx.coroutines.test.runTest
//import kotlin.test.BeforeTest
//import kotlin.test.Test
//import kotlin.test.assertFalse
//import kotlin.test.assertTrue
//
//class GetIsOrderAvailableUseCaseTest {
//
//    @MockK
//    private lateinit var orderAvailableRepository: OrderAvailableRepository
//
//    @InjectMockKs
//    private lateinit var isOrderAvailableUseCase: IsOrderAvailableUseCase
//
//    @BeforeTest
//    fun setup() {
//        MockKAnnotations.init(this)
//    }
//
//    @Test
//    fun `invoke returns true when order is available`() = runTest {
//        // Given
//        val orderAvailable = orderAvailabilityMock.copy(available = true)
//        coEvery { orderAvailableRepository.getOrderAvailable() } returns orderAvailable
//
//        // When
//        val result = isOrderAvailableUseCase.invoke()
//
//        // Then
//        assertTrue(result)
//    }
//
//    @Test
//    fun `invoke returns false when order is not available`() = runTest {
//        // Given
//        val orderAvailable = orderAvailabilityMock.copy(available = false)
//        coEvery { orderAvailableRepository.getOrderAvailable() } returns orderAvailable
//
//        // When
//        val result = isOrderAvailableUseCase.invoke()
//
//        // Then
//        assertFalse(result)
//    }
//
//    @Test
//    fun `invoke returns default true when order is null`() = runTest {
//        // Given
//        coEvery { orderAvailableRepository.getOrderAvailable() } returns null
//
//        // When
//        val result = isOrderAvailableUseCase.invoke()
//
//        // Then
//        assertTrue(result)
//    }
//
//    val orderAvailabilityMock = OrderAvailability(available = true)
//}
