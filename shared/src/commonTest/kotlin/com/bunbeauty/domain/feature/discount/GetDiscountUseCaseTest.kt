package com.bunbeauty.domain.feature.discount

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCase
import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCaseImpl
import com.bunbeauty.shared.domain.model.Discount
import com.bunbeauty.shared.domain.model.date_time.Date
import com.bunbeauty.shared.domain.model.date_time.DateTime
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.domain.repo.DiscountRepo
import com.bunbeauty.shared.domain.repo.OrderRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetDiscountUseCaseTest {
    private val discountRepository: DiscountRepo = mock()
    private val orderRepository: OrderRepo = mock()
    private val dataStoreRepo: DataStoreRepo = mock()
    private val getDiscountUseCase: GetDiscountUseCase =
        GetDiscountUseCaseImpl(
            discountRepository = discountRepository,
            orderRepository = orderRepository,
            dataStoreRepo = dataStoreRepo,
        )

    @Test
    fun `should return firstDiscount 10 when token is null`() =
        runTest {
            // Given

            everySuspend { dataStoreRepo.getToken() } returns null
            everySuspend { dataStoreRepo.getUserUuid() } returns "uuid"
            everySuspend { discountRepository.getDiscount() } returns Discount(10)

            // When
            val discount = getDiscountUseCase()

            // Then
            assertEquals(
                expected = 10,
                actual = discount?.firstOrderDiscount,
            )
        }

    @Test
    fun `should return firstDiscount 10 when userUuid is null`() =
        runTest {
            // Given

            everySuspend { dataStoreRepo.getToken() } returns "token"
            everySuspend { dataStoreRepo.getUserUuid() } returns null
            everySuspend { discountRepository.getDiscount() } returns Discount(10)

            // When
            val discount = getDiscountUseCase()

            // Then
            assertEquals(
                expected = 10,
                actual = discount?.firstOrderDiscount,
            )
        }

    @Test
    fun `should return firstDiscount 10 when lastOrder is null`() =
        runTest {
            // Given

            everySuspend { dataStoreRepo.getToken() } returns "token"
            everySuspend { dataStoreRepo.getUserUuid() } returns "userUuid"
            everySuspend {
                orderRepository.getLastOrderByUserUuidLocalFirst(
                    "token",
                    "userUuid",
                )
            } returns null

            everySuspend { discountRepository.getDiscount() } returns Discount(10)

            // When
            val discount = getDiscountUseCase()

            // Then
            assertEquals(
                expected = 10,
                actual = discount?.firstOrderDiscount,
            )
        }

    @Test
    fun `should return null when lastOrder is not empty`() =
        runTest {
            // Given

            everySuspend { dataStoreRepo.getToken() } returns "token"
            everySuspend { dataStoreRepo.getUserUuid() } returns "userUuid"
            everySuspend {
                orderRepository.getLastOrderByUserUuidLocalFirst(
                    "token",
                    "userUuid",
                )
            } returns
                LightOrder(
                    uuid = "uuid",
                    status = OrderStatus.DONE,
                    code = "code",
                    dateTime =
                        DateTime(
                            date =
                                Date(
                                    dayOfMonth = 5474,
                                    monthNumber = 7337,
                                    year = 1992,
                                ),
                            time = Time(hours = 3796, minutes = 8009),
                        ),
                )

            everySuspend { discountRepository.getDiscount() } returns Discount(10)

            // When
            val discount = getDiscountUseCase()

            // Then
            assertEquals(
                expected = null,
                actual = discount,
            )
        }
}
