package com.bunbeauty.domain.feature.address

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.feature.address.GetCurrentUserAddressUseCase
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.repo.UserAddressRepo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetCurrentUserAddressUseCaseTest {

    private val fakeUserUuid = "userUuid"
    private val fakeSelectedCityUuid = "selectedCityUuid"
    private val userAddressMock: UserAddress = mockk(name = "userAddressMock")

    @MockK
    private val dataStoreRepo: DataStoreRepo = mockk {
        coEvery { getUserUuid() } returns fakeUserUuid
        coEvery { getSelectedCityUuid() } returns fakeSelectedCityUuid
    }

    @MockK
    private lateinit var userAddressRepo: UserAddressRepo

    @InjectMockKs
    private lateinit var getCurrentUserAddressUseCase: GetCurrentUserAddressUseCase

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `return selected address address is selected`() = runTest {
        coEvery {
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
        coEvery {
            userAddressRepo.getSelectedAddressByUserAndCityUuid(
                userUuid = fakeUserUuid,
                cityUuid = fakeSelectedCityUuid
            )
        } returns null
        coEvery {
            userAddressRepo.getFirstUserAddressByUserAndCityUuid(
                userUuid = fakeUserUuid,
                cityUuid = fakeSelectedCityUuid
            )
        } returns userAddressMock

        val currentUserAddress = getCurrentUserAddressUseCase()

        assertEquals(userAddressMock, currentUserAddress)
    }
}
