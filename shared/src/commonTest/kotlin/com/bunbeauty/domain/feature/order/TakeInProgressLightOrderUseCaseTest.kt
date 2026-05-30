package com.bunbeauty.domain.feature.order

import com.bunbeauty.core.domain.order.TakeInProgressLightOrderUseCase
import com.bunbeauty.core.domain.order.TakeInProgressLightOrderUseCaseImpl
import com.bunbeauty.core.model.date_time.Date
import com.bunbeauty.core.model.date_time.DateTime
import com.bunbeauty.core.model.date_time.Time
import com.bunbeauty.core.model.order.LightOrder
import com.bunbeauty.core.model.order.OrderStatus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class TakeInProgressLightOrderUseCaseTest {
    private val useCase: TakeInProgressLightOrderUseCase = TakeInProgressLightOrderUseCaseImpl()

    @Test
    fun `returns null when light order is null`() {
        assertNull(useCase(null))
    }

    @Test
    fun `returns light order when status is accepted`() {
        val lightOrder = lightOrder(status = OrderStatus.ACCEPTED)

        assertEquals(lightOrder, useCase(lightOrder))
    }

    @Test
    fun `returns light order when status is preparing`() {
        val lightOrder = lightOrder(status = OrderStatus.PREPARING)

        assertEquals(lightOrder, useCase(lightOrder))
    }

    @Test
    fun `returns light order when status is done`() {
        val lightOrder = lightOrder(status = OrderStatus.DONE)

        assertEquals(lightOrder, useCase(lightOrder))
    }

    @Test
    fun `returns null when status is delivered`() {
        assertNull(useCase(lightOrder(status = OrderStatus.DELIVERED)))
    }

    @Test
    fun `returns null when status is canceled`() {
        assertNull(useCase(lightOrder(status = OrderStatus.CANCELED)))
    }

    private fun lightOrder(status: OrderStatus): LightOrder =
        LightOrder(
            uuid = "order-uuid",
            status = status,
            code = "01",
            dateTime = DateTime(Date(1, 2, 3), Time(20, 20)),
        )
}
