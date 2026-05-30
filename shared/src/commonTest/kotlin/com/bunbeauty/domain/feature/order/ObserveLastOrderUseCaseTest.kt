package com.bunbeauty.domain.feature.order

import com.bunbeauty.core.domain.order.LightOrderMapper
import com.bunbeauty.core.domain.order.ObserveLastOrderUseCase
import com.bunbeauty.core.domain.order.TakeInProgressLightOrderUseCaseImpl
import com.bunbeauty.core.domain.repo.OrderRepo
import com.bunbeauty.core.model.date_time.Date
import com.bunbeauty.core.model.date_time.DateTime
import com.bunbeauty.core.model.date_time.Time
import com.bunbeauty.core.model.order.LightOrder
import com.bunbeauty.core.model.order.Order
import com.bunbeauty.core.model.order.OrderAddress
import com.bunbeauty.core.model.order.OrderStatus
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ObserveLastOrderUseCaseTest {
    private val orderRepo: OrderRepo = mock()
    private val lightOrderMapper = LightOrderMapper()

    private val observeLastOrderUseCase: ObserveLastOrderUseCase =
        ObserveLastOrderUseCase(
            orderRepo = orderRepo,
            lightOrderMapper = lightOrderMapper,
            takeInProgressLightOrderUseCase = TakeInProgressLightOrderUseCaseImpl(),
        )

    @Test
    fun `emits done last order`() =
        runTest {
            val doneOrder = lightOrder(status = OrderStatus.DONE)
            everySuspend { orderRepo.getLastOrderByUserUuidNetworkFirst() } returns doneOrder
            everySuspend { orderRepo.observeOrderUpdates() } returns (null to flowOf())

            val (_, lastOrderFlow) = observeLastOrderUseCase()

            assertEquals(doneOrder, lastOrderFlow.first())
        }

    @Test
    fun `returns null flow when last order is delivered`() =
        runTest {
            everySuspend { orderRepo.getLastOrderByUserUuidNetworkFirst() } returns
                lightOrder(status = OrderStatus.DELIVERED)

            val (_, lastOrderFlow) = observeLastOrderUseCase()

            assertNull(lastOrderFlow.first())
        }

    @Test
    fun `returns null flow when last order is canceled`() =
        runTest {
            everySuspend { orderRepo.getLastOrderByUserUuidNetworkFirst() } returns
                lightOrder(status = OrderStatus.CANCELED)

            val (_, lastOrderFlow) = observeLastOrderUseCase()

            assertNull(lastOrderFlow.first())
        }

    @Test
    fun `emits in progress last order`() =
        runTest {
            val activeOrder = lightOrder(status = OrderStatus.PREPARING)
            everySuspend { orderRepo.getLastOrderByUserUuidNetworkFirst() } returns activeOrder
            everySuspend { orderRepo.observeOrderUpdates() } returns (null to flowOf())

            val (_, lastOrderFlow) = observeLastOrderUseCase()

            assertEquals(activeOrder, lastOrderFlow.first())
        }

    @Test
    fun `emits null when order update becomes delivered`() =
        runTest {
            val activeOrder = lightOrder(status = OrderStatus.ACCEPTED)
            val deliveredOrder = order(status = OrderStatus.DELIVERED, uuid = activeOrder.uuid)
            everySuspend { orderRepo.getLastOrderByUserUuidNetworkFirst() } returns activeOrder
            everySuspend { orderRepo.observeOrderUpdates() } returns (
                "observation-uuid" to flowOf(deliveredOrder)
            )

            val (_, lastOrderFlow) = observeLastOrderUseCase()

            val emissions = lastOrderFlow.take(2).toList()
            assertEquals(activeOrder, emissions[0])
            assertNull(emissions[1])
        }

    private fun lightOrder(status: OrderStatus): LightOrder =
        LightOrder(
            uuid = "order-uuid",
            status = status,
            code = "01",
            dateTime = DateTime(Date(1, 2, 3), Time(20, 20)),
        )

    private fun order(
        status: OrderStatus,
        uuid: String = "order-uuid",
    ): Order =
        Order(
            uuid = uuid,
            code = "01",
            status = status,
            dateTime = DateTime(Date(1, 2, 3), Time(20, 20)),
            isDelivery = false,
            deferredTime = null,
            address =
                OrderAddress(
                    description = null,
                    street = null,
                    house = null,
                    flat = null,
                    entrance = null,
                    floor = null,
                    comment = null,
                ),
            comment = null,
            deliveryCost = null,
            orderProductList = emptyList(),
            paymentMethod = null,
            oldTotalCost = null,
            newTotalCost = 100,
            percentDiscount = null,
        )
}
