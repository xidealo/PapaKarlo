package com.bunbeauty.domain.feature.order

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.feature.order.GetLastOrderUseCase
import com.bunbeauty.shared.domain.model.date_time.Date
import com.bunbeauty.core.model.date_time.DateTime
import com.bunbeauty.core.model.date_time.Time
import com.bunbeauty.core.model.order.LightOrder
import com.bunbeauty.core.model.order.OrderStatus
import com.bunbeauty.core.domain.repo.OrderRepo
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetLastOrderUseCaseTest {
    private val orderRepo: OrderRepo = mock(MockMode.autofill)

    private val dataStoreRepo: DataStoreRepo = mock(MockMode.autofill)

    private val getLastOrderUseCase: GetLastOrderUseCase =
        GetLastOrderUseCase(
            orderRepo = orderRepo,
            dataStoreRepo = dataStoreRepo,
        )

    @Test
    fun `return null when token are invalid`() =
        runTest {
            everySuspend { dataStoreRepo.getToken() } returns null
            assertEquals(null, getLastOrderUseCase())
        }

    @Test
    fun `return null when user are not authorized`() =
        runTest {
            everySuspend { dataStoreRepo.getUserUuid() } returns null
            assertEquals(null, getLastOrderUseCase())
        }

    @Test
    fun `return null when user are authorized but doesn't have last order `() =
        runTest {
            everySuspend { orderRepo.getLastOrderByUserUuidLocalFirst(any(), any()) } returns null
            assertEquals(null, getLastOrderUseCase())
        }

    @Test
    fun `return lightOrder when user are authorized and has last order`() =
        runTest {
            val uuid = "123"
            val status = OrderStatus.ACCEPTED
            val code = "01"
            val dateTime = DateTime(Date(1, 2, 3), Time(20, 20))
            val lightOrder =
                LightOrder(
                    uuid = uuid,
                    status = status,
                    code = code,
                    dateTime = dateTime,
                )
            everySuspend { orderRepo.getLastOrderByUserUuidLocalFirst(any(), any()) } returns lightOrder

            assertEquals(lightOrder, getLastOrderUseCase())
        }
}
