package com.bunbeauty.domain.feature.address

import com.bunbeauty.core.domain.address.GetCurrentUserAddressWithCityUseCaseImpl
import com.bunbeauty.core.domain.city.GetSelectedCityUseCase
import com.bunbeauty.core.domain.repo.UserAddressRepo
import com.bunbeauty.core.model.address.UserAddress
import com.bunbeauty.core.model.address.UserAddressWithCity
import com.bunbeauty.getCity
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetCurrentUserAddressWithCityUseCaseTest {
    private val fakeCity =
        getCity(
            "Kimry",
        )
    private val userAddressMock =
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
    private val getSelectedCityUseCaseImpl: GetSelectedCityUseCase = mock()

    private val getCurrentUserAddressWithCityUseCaseImpl: GetCurrentUserAddressWithCityUseCaseImpl =
        GetCurrentUserAddressWithCityUseCaseImpl(
            userAddressRepo = userAddressRepo,
            getSelectedCityUseCase = getSelectedCityUseCaseImpl,
        )

    @Test
    fun `return selected address with city address  is selected`() =
        runTest {
            everySuspend {
                getSelectedCityUseCaseImpl()
            } returns fakeCity

            everySuspend {
                userAddressRepo.getSelectedAddressByUserAndCityUuid()
            } returns userAddressMock

            val currentUserAddress = getCurrentUserAddressWithCityUseCaseImpl()

            assertEquals(
                UserAddressWithCity(
                    userAddressMock,
                    fakeCity.name,
                ),
                currentUserAddress,
            )
        }

    @Test
    fun `return first address with city when address is not selected`() =
        runTest {
            everySuspend {
                userAddressRepo.getFirstUserAddressByUserAndCityUuid()
            } returns userAddressMock

            everySuspend {
                userAddressRepo.getSelectedAddressByUserAndCityUuid()
            } returns userAddressMock

            everySuspend {
                getSelectedCityUseCaseImpl()
            } returns fakeCity

            val currentUserAddress = getCurrentUserAddressWithCityUseCaseImpl()

            assertEquals(
                UserAddressWithCity(
                    userAddressMock,
                    fakeCity.name,
                ),
                currentUserAddress,
            )
        }
}
