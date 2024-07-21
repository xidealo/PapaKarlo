package com.bunbeauty.domain.feature.discount

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.repository.DiscountRepository
import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCase
import com.bunbeauty.shared.domain.model.Discount
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.repo.OrderRepo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetDiscountUseCaseTest {

    private val discountRepository: DiscountRepository = mockk()
    private val orderRepository: OrderRepo = mockk()
    private val dataStoreRepo: DataStoreRepo = mockk()
    private lateinit var getDiscountUseCase: GetDiscountUseCase

    @BeforeTest
    fun setup() {
        getDiscountUseCase = GetDiscountUseCase(
            discountRepository = discountRepository,
            orderRepository = orderRepository,
            dataStoreRepo = dataStoreRepo
        )
    }

    @Test
    fun `should return firstDiscount 10 when token is null`() = runTest {
        // Given

        coEvery { dataStoreRepo.getToken() } returns null
        coEvery { dataStoreRepo.getUserUuid() } returns "uuid"
        coEvery { discountRepository.getDiscount() } returns Discount(10)

        // When
        val discount = getDiscountUseCase()

        // Then
        assertEquals(
            expected = 10,
            actual = discount?.firstOrderDiscount
        )
    }

    @Test
    fun `should return firstDiscount 10 when userUuid is null`() = runTest {
        // Given

        coEvery { dataStoreRepo.getToken() } returns "token"
        coEvery { dataStoreRepo.getUserUuid() } returns null
        coEvery { discountRepository.getDiscount() } returns Discount(10)

        // When
        val discount = getDiscountUseCase()

        // Then
        assertEquals(
            expected = 10,
            actual = discount?.firstOrderDiscount
        )
    }

    @Test
    fun `should return firstDiscount 10 when lastOrder is null`() = runTest {
        // Given

        coEvery { dataStoreRepo.getToken() } returns "token"
        coEvery { dataStoreRepo.getUserUuid() } returns "userUuid"
        coEvery {
            orderRepository.getLastOrderByUserUuidLocalFirst(
                "token",
                "userUuid"
            )
        } returns null

        coEvery { discountRepository.getDiscount() } returns Discount(10)

        // When
        val discount = getDiscountUseCase()

        // Then
        assertEquals(
            expected = 10,
            actual = discount?.firstOrderDiscount
        )
    }

    @Test
    fun `should return null when lastOrder is not empty`() = runTest {
        // Given

        coEvery { dataStoreRepo.getToken() } returns "token"
        coEvery { dataStoreRepo.getUserUuid() } returns "userUuid"
        coEvery {
            orderRepository.getLastOrderByUserUuidLocalFirst(
                "token",
                "userUuid"
            )
        } returns LightOrder(
            uuid = "uuid",
            status = mockk(),
            code = "code",
            dateTime = mockk()
        )

        coEvery { discountRepository.getDiscount() } returns Discount(10)

        // When
        val discount = getDiscountUseCase()

        // Then
        assertEquals(
            expected = null,
            actual = discount
        )
    }
}
