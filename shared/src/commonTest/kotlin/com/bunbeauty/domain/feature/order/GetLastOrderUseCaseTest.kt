//package com.bunbeauty.domain.feature.order
//
//import com.bunbeauty.shared.DataStoreRepo
//import com.bunbeauty.shared.domain.feature.order.GetLastOrderUseCase
//import com.bunbeauty.shared.domain.model.date_time.Date
//import com.bunbeauty.shared.domain.model.date_time.DateTime
//import com.bunbeauty.shared.domain.model.date_time.Time
//import com.bunbeauty.shared.domain.model.order.LightOrder
//import com.bunbeauty.shared.domain.model.order.OrderStatus
//import com.bunbeauty.shared.domain.repo.OrderRepo
//
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runTest
//import kotlinx.coroutines.test.setMain
//import kotlin.test.BeforeTest
//import kotlin.test.Test
//import kotlin.test.assertEquals
//
//@OptIn(ExperimentalCoroutinesApi::class)
//internal class GetLastOrderUseCaseTest {
//
//    @MockK(relaxed = true)
//    private lateinit var orderRepo: OrderRepo
//
//    @MockK(relaxed = true)
//    private lateinit var dataStoreRepo: DataStoreRepo
//
//    @InjectMockKs
//    private lateinit var getLastOrderUseCase: GetLastOrderUseCase
//
//    @BeforeTest
//    fun setup() {
//        MockKAnnotations.init(this)
//        Dispatchers.setMain(Dispatchers.Unconfined)
//    }
//
//    @Test
//    fun `return null when token are invalid`() = runTest {
//        coEvery { dataStoreRepo.getToken() } returns null
//        assertEquals(null, getLastOrderUseCase())
//    }
//
//    @Test
//    fun `return null when user are not authorized`() = runTest {
//        coEvery { dataStoreRepo.getUserUuid() } returns null
//        assertEquals(null, getLastOrderUseCase())
//    }
//
//    @Test
//    fun `return null when user are authorized but doesn't have last order `() = runTest {
//        coEvery { orderRepo.getLastOrderByUserUuidLocalFirst(any(), any()) } returns null
//        assertEquals(null, getLastOrderUseCase())
//    }
//
//    @Test
//    fun `return lightOrder when user are authorized and has last order`() = runTest {
//        val uuid = "123"
//        val status = OrderStatus.ACCEPTED
//        val code = "01"
//        val dateTime = DateTime(Date(1, 2, 3), Time(20, 20))
//        val lightOrder = LightOrder(
//            uuid = uuid,
//            status = status,
//            code = code,
//            dateTime = dateTime
//        )
//        coEvery { orderRepo.getLastOrderByUserUuidLocalFirst(any(), any()) } returns lightOrder
//
//        assertEquals(lightOrder, getLastOrderUseCase())
//    }
//}
