// package com.bunbeauty.domain.feature.motivation
//
// import com.bunbeauty.shared.domain.feature.address.GetCurrentUserAddressUseCase
// import com.bunbeauty.shared.domain.feature.motivation.GetMotivationUseCase
// import com.bunbeauty.shared.domain.feature.motivation.Motivation
// import com.bunbeauty.shared.domain.model.address.UserAddress
// import kotlinx.coroutines.test.runTest
// import kotlin.test.BeforeTest
// import kotlin.test.Test
// import kotlin.test.assertEquals
// import kotlin.test.assertNull
//
// class GetMotivationUseCaseTest {
//
//    private val getCurrentUserAddressUseCase: GetCurrentUserAddressUseCase
//
//    private val getMotivationUseCase: GetMotivationUseCase = GetMotivationUseCase()
//
//    @Test
//    fun `return null when no min order cost and no low delivery`() = runTest {
//        val userAddress: UserAddress = mock {
//            everySuspend { minOrderCost } returns null
//            everySuspend { forLowDeliveryCost } returns null
//            everySuspend { lowDeliveryCost } returns null
//        }
//        everySuspend { getCurrentUserAddressUseCase() } returns userAddress
//
//        val motivation = getMotivationUseCase(newTotalCost = 100)
//
//        assertNull(motivation)
//    }
//
//    @Test
//    fun `return MinOrderCost motivation when cost is less then min order cost`() = runTest {
//        val userAddress: UserAddress = mock {
//            everySuspend { minOrderCost } returns 500
//            everySuspend { forLowDeliveryCost } returns null
//            everySuspend { lowDeliveryCost } returns null
//        }
//        everySuspend { getCurrentUserAddressUseCase() } returns userAddress
//        val expected = Motivation.MinOrderCost(cost = 500)
//
//        val motivation = getMotivationUseCase(newTotalCost = 100)
//
//        assertEquals(expected, motivation)
//    }
//
//    @Test
//    fun `return null when cost is more then min order cost`() = runTest {
//        val userAddress: UserAddress = mock {
//            everySuspend { minOrderCost } returns 500
//            everySuspend { forLowDeliveryCost } returns null
//            everySuspend { lowDeliveryCost } returns null
//        }
//        everySuspend { getCurrentUserAddressUseCase() } returns userAddress
//
//        val motivation = getMotivationUseCase(newTotalCost = 520)
//
//        assertNull(motivation)
//    }
//
//    @Test
//    fun `return ForLowerDelivery motivation when cost is less then needed for free delivery`() =
//        runTest {
//            val userAddress: UserAddress = mock {
//                everySuspend { minOrderCost } returns 500
//                everySuspend { forLowDeliveryCost } returns 1000
//                everySuspend { lowDeliveryCost } returns 0
//            }
//            everySuspend { getCurrentUserAddressUseCase() } returns userAddress
//            val expected = Motivation.ForLowerDelivery(
//                increaseAmountBy = 400,
//                progress = 0.6f,
//                isFree = true
//            )
//
//            val motivation = getMotivationUseCase(newTotalCost = 600)
//
//            assertEquals(expected, motivation)
//        }
//
//    @Test
//    fun `return LowerDeliveryAchieved motivation when cost is more then needed for lower delivery`() =
//        runTest {
//            val userAddress: UserAddress = mock {
//                everySuspend { minOrderCost } returns null
//                everySuspend { forLowDeliveryCost } returns 1000
//                everySuspend { lowDeliveryCost } returns 100
//            }
//            everySuspend { getCurrentUserAddressUseCase() } returns userAddress
//            val expected = Motivation.LowerDeliveryAchieved(isFree = false)
//
//            val motivation = getMotivationUseCase(newTotalCost = 1200)
//
//            assertEquals(expected, motivation)
//        }
// }
