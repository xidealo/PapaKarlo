package com.bunbeauty.domain.feature.order

import com.bunbeauty.core.domain.order.GetLastOrderUseCase
import com.bunbeauty.core.domain.order.TakeInProgressLightOrderUseCaseImpl
import com.bunbeauty.core.domain.repo.OrderRepo
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
import kotlin.test.assertNull

class GetLastOrderUseCaseTest {
    private val orderRepo: OrderRepo = mock()

    private val getLastOrderUseCase: GetLastOrderUseCase =
        GetLastOrderUseCase(
            orderRepo = orderRepo,
            takeInProgressLightOrderUseCase = TakeInProgressLightOrderUseCaseImpl(),
        )

    @Test
    fun `returns null when repository has no last order`() =
        runTest {
            everySuspend { orderRepo.getLastOrderByUserUuidLocalFirst() } returns null

            assertNull(getLastOrderUseCase())
        }

    @Test
    fun `returns light order when repository has in progress last order`() =
        runTest {
            val lightOrder =
                LightOrder(
                    uuid = "123",
                    status = OrderStatus.ACCEPTED,
                    code = "01",
                    dateTime = DateTime(Date(1, 2, 3), Time(20, 20)),
                )
            everySuspend { orderRepo.getLastOrderByUserUuidLocalFirst() } returns lightOrder

            assertEquals(lightOrder, getLastOrderUseCase())
        }

    @Test
    fun `returns light order when last order is done`() =
        runTest {
            val lightOrder =
                LightOrder(
                    uuid = "123",
                    status = OrderStatus.DONE,
                    code = "01",
                    dateTime = DateTime(Date(1, 2, 3), Time(20, 20)),
                )
            everySuspend { orderRepo.getLastOrderByUserUuidLocalFirst() } returns lightOrder

            assertEquals(lightOrder, getLastOrderUseCase())
        }

    @Test
    fun `returns null when last order is delivered`() =
        runTest {
            everySuspend { orderRepo.getLastOrderByUserUuidLocalFirst() } returns
                LightOrder(
                    uuid = "123",
                    status = OrderStatus.DELIVERED,
                    code = "01",
                    dateTime = DateTime(Date(1, 2, 3), Time(20, 20)),
                )

            assertNull(getLastOrderUseCase())
        }

    @Test
    fun `returns null when last order is canceled`() =
        runTest {
            everySuspend { orderRepo.getLastOrderByUserUuidLocalFirst() } returns
                LightOrder(
                    uuid = "123",
                    status = OrderStatus.CANCELED,
                    code = "01",
                    dateTime = DateTime(Date(1, 2, 3), Time(20, 20)),
                )

            assertNull(getLastOrderUseCase())
        }
}
