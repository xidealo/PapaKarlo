package com.bunbeauty.domain.feature.motivation

import com.bunbeauty.shared.domain.feature.address.GetCurrentUserAddressUseCase
import com.bunbeauty.shared.domain.feature.motivation.GetMotivationUseCase
import com.bunbeauty.shared.domain.feature.motivation.Motivation
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.use_case.address.GetUserAddressListUseCase
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GetMotivationUseCaseTest {

    private val getCurrentUserAddressUseCase: GetCurrentUserAddressUseCase = mock()
    private val getUserAddressListUseCase: GetUserAddressListUseCase = mock(MockMode.autofill)

    private val getMotivationUseCase: GetMotivationUseCase = GetMotivationUseCase(
        getCurrentUserAddressUseCase = getCurrentUserAddressUseCase,
        getUserAddressListUseCase = getUserAddressListUseCase
    )

    @Test
    fun `return null when no min order cost and no low delivery`() = runTest {
        val userAddress: UserAddress = userAddress.copy(
            minOrderCost = null,
            forLowDeliveryCost = null,
            lowDeliveryCost = null
        )

        everySuspend { getCurrentUserAddressUseCase() } returns userAddress

        val motivation = getMotivationUseCase(newTotalCost = 100, isDelivery = true)

        assertNull(motivation)
    }

    @Test
    fun `return MinOrderCost motivation when cost is less then min order cost`() = runTest {
        val userAddress: UserAddress = userAddress.copy(
            minOrderCost = 500,
            forLowDeliveryCost = null,
            lowDeliveryCost = null
        )
        everySuspend { getCurrentUserAddressUseCase() } returns userAddress
        val expected = Motivation.MinOrderCost(cost = 500)

        val motivation = getMotivationUseCase(newTotalCost = 100, isDelivery = true)

        assertEquals(expected, motivation)
    }

    @Test
    fun `return null when cost is more then min order cost`() = runTest {
        val userAddress: UserAddress = userAddress.copy(
            minOrderCost = 500,
            forLowDeliveryCost = null,
            lowDeliveryCost = null
        )
        everySuspend { getCurrentUserAddressUseCase() } returns userAddress

        val motivation = getMotivationUseCase(newTotalCost = 520, isDelivery = true)

        assertNull(motivation)
    }

    @Test
    fun `return ForLowerDelivery motivation when cost is less then needed for free delivery`() =
        runTest {
            val userAddress: UserAddress = userAddress.copy(
                minOrderCost = 500,
                forLowDeliveryCost = 1000,
                lowDeliveryCost = 0
            )
            everySuspend { getCurrentUserAddressUseCase() } returns userAddress
            val expected = Motivation.ForLowerDelivery(
                increaseAmountBy = 400,
                progress = 0.6f,
                isFree = true
            )

            val motivation = getMotivationUseCase(newTotalCost = 600, isDelivery = true)

            assertEquals(expected, motivation)
        }

    @Test
    fun `return LowerDeliveryAchieved motivation when cost is more then needed for lower delivery`() =
        runTest {
            val userAddress: UserAddress = userAddress.copy(
                minOrderCost = null,
                forLowDeliveryCost = 1000,
                lowDeliveryCost = 100
            )
            everySuspend { getCurrentUserAddressUseCase() } returns userAddress
            val expected = Motivation.LowerDeliveryAchieved(isFree = false)

            val motivation = getMotivationUseCase(newTotalCost = 1200, isDelivery = true)

            assertEquals(expected, motivation)
        }

    @Test
    fun `return null motivation when delivery is false`() =
        runTest {
            val userAddress: UserAddress = userAddress.copy(
                minOrderCost = null,
                forLowDeliveryCost = 1000,
                lowDeliveryCost = 100
            )
            everySuspend { getCurrentUserAddressUseCase() } returns userAddress
            val expected = null

            val motivation = getMotivationUseCase(newTotalCost = 1200, isDelivery = false)

            assertEquals(expected, motivation)
        }

    private val userAddress = UserAddress(
        uuid = "",
        street = "",
        house = "",
        flat = null,
        entrance = null,
        floor = null,
        comment = null,
        minOrderCost = null,
        normalDeliveryCost = 0,
        forLowDeliveryCost = null,
        lowDeliveryCost = null,
        userUuid = "",
        cafeUuid = "cafeUuid"
    )
}
