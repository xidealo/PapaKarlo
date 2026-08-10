package com.bunbeauty.domain.feature.discount

import com.bunbeauty.core.domain.discount.GetDiscountUseCase
import com.bunbeauty.core.domain.discount.GetDiscountUseCaseImpl
import com.bunbeauty.core.domain.repo.DiscountRepo
import com.bunbeauty.core.domain.repo.OrderRepo
import com.bunbeauty.core.domain.repo.SettingsRepo
import com.bunbeauty.core.model.Discount
import com.bunbeauty.core.model.Settings
import com.bunbeauty.core.model.date_time.Date
import com.bunbeauty.core.model.date_time.DateTime
import com.bunbeauty.core.model.date_time.Time
import com.bunbeauty.core.model.order.LightOrder
import com.bunbeauty.core.model.order.OrderStatus
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetDiscountUseCaseTest {
    private val discountRepository: DiscountRepo = mock()
    private val orderRepository: OrderRepo = mock()
    private val settingsRepo: SettingsRepo = mock()
    private val getDiscountUseCase: GetDiscountUseCase =
        GetDiscountUseCaseImpl(
            discountRepository = discountRepository,
            orderRepository = orderRepository,
            settingsRepo = settingsRepo,
        )

    @Test
    fun `should return firstDiscount 10 when token is null`() =
        runTest {
            everySuspend { settingsRepo.getSettings() } returns null
            everySuspend { discountRepository.getDiscount() } returns Discount(10)
            everySuspend { orderRepository.getLastOrderByUserUuidLocalFirst() } returns null

            val discount = getDiscountUseCase()

            assertEquals(
                expected = 10,
                actual = discount?.firstOrderDiscount,
            )
        }

    @Test
    fun `should return firstDiscount 10 when userUuid is null`() =
        runTest {
            everySuspend { settingsRepo.getSettings() } returns null
            everySuspend { discountRepository.getDiscount() } returns Discount(10)
            everySuspend { orderRepository.getLastOrderByUserUuidLocalFirst() } returns null

            val discount = getDiscountUseCase()

            assertEquals(
                expected = 10,
                actual = discount?.firstOrderDiscount,
            )
        }

    @Test
    fun `should return firstDiscount 10 when lastOrder is null`() =
        runTest {
            everySuspend { settingsRepo.getSettings() } returns null
            everySuspend {
                orderRepository.getLastOrderByUserUuidLocalFirst()
            } returns null
            everySuspend { discountRepository.getDiscount() } returns Discount(10)

            val discount = getDiscountUseCase()

            assertEquals(
                expected = 10,
                actual = discount?.firstOrderDiscount,
            )
        }

    @Test
    fun `should return null when lastOrder is not empty`() =
        runTest {
            everySuspend { settingsRepo.getSettings() } returns null
            everySuspend {
                orderRepository.getLastOrderByUserUuidLocalFirst()
            } returns lightOrder()
            everySuspend { discountRepository.getDiscount() } returns Discount(10)

            val discount = getDiscountUseCase()

            assertEquals(
                expected = null,
                actual = discount,
            )
        }

    @Test
    fun `should return personal discount when personalDiscountPercent is set and lastOrder exists`() =
        runTest {
            everySuspend { settingsRepo.getSettings() } returns settings(personalDiscountPercent = 15)
            everySuspend {
                orderRepository.getLastOrderByUserUuidLocalFirst()
            } returns lightOrder()
            everySuspend { discountRepository.getDiscount() } returns Discount(10)

            val discount = getDiscountUseCase()

            assertEquals(
                expected = 15,
                actual = discount?.firstOrderDiscount,
            )
        }

    @Test
    fun `should prefer personal discount over company first-order discount`() =
        runTest {
            everySuspend { settingsRepo.getSettings() } returns settings(personalDiscountPercent = 15)
            everySuspend {
                orderRepository.getLastOrderByUserUuidLocalFirst()
            } returns null
            everySuspend { discountRepository.getDiscount() } returns Discount(10)

            val discount = getDiscountUseCase()

            assertEquals(
                expected = 15,
                actual = discount?.firstOrderDiscount,
            )
        }

    @Test
    fun `should return company discount when personalDiscountPercent is null and lastOrder is null`() =
        runTest {
            everySuspend { settingsRepo.getSettings() } returns settings(personalDiscountPercent = null)
            everySuspend {
                orderRepository.getLastOrderByUserUuidLocalFirst()
            } returns null
            everySuspend { discountRepository.getDiscount() } returns Discount(10)

            val discount = getDiscountUseCase()

            assertEquals(
                expected = 10,
                actual = discount?.firstOrderDiscount,
            )
        }

    @Test
    fun `should return null when personalDiscountPercent is null and lastOrder exists`() =
        runTest {
            everySuspend { settingsRepo.getSettings() } returns settings(personalDiscountPercent = null)
            everySuspend {
                orderRepository.getLastOrderByUserUuidLocalFirst()
            } returns lightOrder()
            everySuspend { discountRepository.getDiscount() } returns Discount(10)

            val discount = getDiscountUseCase()

            assertEquals(
                expected = null,
                actual = discount,
            )
        }

    private fun settings(personalDiscountPercent: Int?) =
        Settings(
            userUuid = "userUuid",
            phoneNumber = "+79001234567",
            email = null,
            personalDiscountPercent = personalDiscountPercent,
        )

    private fun lightOrder() =
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
}
