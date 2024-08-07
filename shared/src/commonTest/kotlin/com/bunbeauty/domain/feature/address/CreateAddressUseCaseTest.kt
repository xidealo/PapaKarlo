package com.bunbeauty.domain.feature.address

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.repository.UserAddressRepository
import com.bunbeauty.shared.domain.exeptions.NoSelectedCityUuidException
import com.bunbeauty.shared.domain.exeptions.NoTokenException
import com.bunbeauty.shared.domain.feature.address.CreateAddressUseCase
import com.bunbeauty.shared.domain.model.Suggestion
import com.bunbeauty.shared.domain.model.address.CreatedUserAddress
import com.bunbeauty.shared.domain.model.address.UserAddress
import io.mockative.Mock
import io.mockative.classOf
import io.mockative.coEvery
import io.mockative.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CreateAddressUseCaseTest {

    @Mock
    private val dataStoreRepo = mock(classOf<DataStoreRepo>())

    @Mock
    private val userAddressRepo = mock(classOf<UserAddressRepository>())

    private val createAddressUseCase: CreateAddressUseCase = CreateAddressUseCase(
        dataStoreRepo = dataStoreRepo,
        userAddressRepo = userAddressRepo
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `return NoTokenException when token is null`() = runTest {
        coEvery { dataStoreRepo.getToken() }.returns(null)

        assertFailsWith(
            exceptionClass = NoTokenException::class,
            block = {
                createAddressUseCase(
                    street = Suggestion(
                        fiasId = "fiasId",
                        street = "street",
                        details = null
                    ),
                    house = "house",
                    flat = "flat",
                    entrance = "entrance",
                    comment = "comment",
                    floor = "floor"
                )
            }
        )
    }

    @Test
    fun `return NoSelectedCityUuidException when selected city uuid is null`() = runTest {
        coEvery { dataStoreRepo.getSelectedCityUuid() }.returns(null)

        assertFailsWith(
            exceptionClass = NoSelectedCityUuidException::class,
            block = {
                createAddressUseCase(
                    street = Suggestion(
                        fiasId = "fiasId",
                        street = "street",
                        details = null
                    ),
                    house = "house",
                    flat = "flat",
                    entrance = "entrance",
                    comment = "comment",
                    floor = "floor"
                )
            }
        )
    }

    @Test
    fun `return userAddress when all data is ok`() = runTest {
        coEvery { dataStoreRepo.getToken() }.returns("token")
        coEvery { dataStoreRepo.getSelectedCityUuid() }.returns("cityUuid")
        val userAddress = UserAddress(
            uuid = "uuid",
            street = "street",
            house = "house",
            flat = "flat",
            entrance = "entrance",
            comment = "comment",
            floor = "floor",
            minOrderCost = null,
            normalDeliveryCost = 100,
            forLowDeliveryCost = null,
            lowDeliveryCost = null,
            userUuid = "userUuid"
        )

        coEvery {
            userAddressRepo.saveUserAddress(
                token = "token",
                createdUserAddress = CreatedUserAddress(
                    street = Suggestion(
                        fiasId = "fiasId",
                        street = "street",
                        details = null
                    ),
                    house = "house",
                    flat = "flat",
                    entrance = "entrance",
                    comment = "comment",
                    floor = "floor",
                    cityUuid = "cityUuid",
                    isVisible = true
                )
            )
        }.returns(userAddress)

        val createdUserAddress = createAddressUseCase(
            street = Suggestion(
                fiasId = "fiasId",
                street = "street",
                details = null
            ),
            house = "house",
            flat = "flat",
            entrance = "entrance",
            comment = "comment",
            floor = "floor"
        )

        assertEquals(userAddress, createdUserAddress)
    }
}
