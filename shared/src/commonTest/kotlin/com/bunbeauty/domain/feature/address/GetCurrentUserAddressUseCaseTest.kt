package com.bunbeauty.domain.feature.address

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.feature.address.GetCurrentUserAddressUseCaseImpl
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.repo.UserAddressRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetCurrentUserAddressUseCaseTest {

    private val fakeUserUuid = "userUuid"
    private val fakeSelectedCityUuid = "selectedCityUuid"
    private val userAddressMock: UserAddress = UserAddress(
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
        cafeUuid = "cafeUuid"
    )

    private val dataStoreRepo: DataStoreRepo = mock {
        everySuspend { getUserUuid() } returns fakeUserUuid
        everySuspend { getSelectedCityUuid() } returns fakeSelectedCityUuid
    }

    private val userAddressRepo: UserAddressRepo = mock()

    private val getCurrentUserAddressUseCase: GetCurrentUserAddressUseCaseImpl =
        GetCurrentUserAddressUseCaseImpl(
            dataStoreRepo = dataStoreRepo,
            userAddressRepo = userAddressRepo
        )

    @Test
    fun `return selected address address is selected`() = runTest {
        everySuspend {
            userAddressRepo.getSelectedAddressByUserAndCityUuid(
                userUuid = fakeUserUuid,
                cityUuid = fakeSelectedCityUuid
            )
        } returns userAddressMock

        val currentUserAddress = getCurrentUserAddressUseCase()

        assertEquals(userAddressMock, currentUserAddress)
    }

    @Test
    fun `return first address when address is not selected`() = runTest {
        everySuspend {
            userAddressRepo.getSelectedAddressByUserAndCityUuid(
                userUuid = fakeUserUuid,
                cityUuid = fakeSelectedCityUuid
            )
        } returns null
        everySuspend {
            userAddressRepo.getFirstUserAddressByUserAndCityUuid(
                userUuid = fakeUserUuid,
                cityUuid = fakeSelectedCityUuid
            )
        } returns userAddressMock

        val currentUserAddress = getCurrentUserAddressUseCase()

        assertEquals(userAddressMock, currentUserAddress)
    }
}
