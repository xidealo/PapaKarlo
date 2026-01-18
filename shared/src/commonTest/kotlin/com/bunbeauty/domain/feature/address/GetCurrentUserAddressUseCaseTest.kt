package com.bunbeauty.domain.feature.address

import com.bunbeauty.core.domain.address.GetCurrentUserAddressUseCaseImpl
import com.bunbeauty.core.domain.repo.UserAddressRepo
import com.bunbeauty.core.model.address.UserAddress
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetCurrentUserAddressUseCaseTest {
    private val userAddressMock: UserAddress =
        UserAddress(
            uuid = "pulvinar",
            street = "conclusionemque",
            house = "purus",
            flat = null,
            entrance = null,
            floor = null,
            comment = null,
            minOrderCost = null,
            normalDeliveryCost = 2028,
            forLowDeliveryCost = null,
            lowDeliveryCost = null,
            userUuid = "his",
            cafeUuid = "cafeUuid",
        )

    private val userAddressRepo: UserAddressRepo = mock()

    private val getCurrentUserAddressUseCase: GetCurrentUserAddressUseCaseImpl =
        GetCurrentUserAddressUseCaseImpl(
            userAddressRepo = userAddressRepo,
        )

    @Test
    fun `return selected address address is selected`() =
        runTest {
            everySuspend {
                userAddressRepo.getSelectedAddressByUserAndCityUuid()
            } returns userAddressMock

            val currentUserAddress = getCurrentUserAddressUseCase()

            assertEquals(userAddressMock, currentUserAddress)
        }

    @Test
    fun `return first address when address is not selected`() =
        runTest {
            everySuspend {
                userAddressRepo.getSelectedAddressByUserAndCityUuid()
            } returns null
            everySuspend {
                userAddressRepo.getFirstUserAddressByUserAndCityUuid()
            } returns userAddressMock

            val currentUserAddress = getCurrentUserAddressUseCase()

            assertEquals(userAddressMock, currentUserAddress)
        }
}
