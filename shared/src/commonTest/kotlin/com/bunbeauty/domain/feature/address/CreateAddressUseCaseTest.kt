package com.bunbeauty.domain.feature.address

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.exeptions.NoSelectedCityUuidException
import com.bunbeauty.shared.domain.exeptions.NoTokenException
import com.bunbeauty.shared.domain.feature.address.CreateAddressUseCase
import com.bunbeauty.shared.domain.model.Suggestion
import com.bunbeauty.shared.domain.model.address.CreatedUserAddress
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.repo.StreetRepo
import com.bunbeauty.shared.domain.repo.UserAddressRepo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
internal class CreateAddressUseCaseTest {

    @MockK(relaxed = true)
    private lateinit var dataStoreRepo: DataStoreRepo

    @MockK(relaxed = true)
    private lateinit var userAddressRepo: UserAddressRepo

    @InjectMockKs
    private lateinit var createAddressUseCase: CreateAddressUseCase

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `return NoTokenException when token is null`() = runTest {
        coEvery { dataStoreRepo.getToken() } returns null

        assertFailsWith(
            exceptionClass = NoTokenException::class,
            block = {
                createAddressUseCase(
                    street = Suggestion(
                        fiasId = "fiasId",
                        street = "street"
                    ),
                    house = "house",
                    flat = "flat",
                    entrance = "entrance",
                    comment = "comment",
                    floor = "floor",
                )
            }
        )
    }

    @Test
    fun `return NoSelectedCityUuidException when selected city uuid is null`() = runTest {
        coEvery { dataStoreRepo.getSelectedCityUuid() } returns null

        assertFailsWith(
            exceptionClass = NoSelectedCityUuidException::class,
            block = {
                createAddressUseCase(
                    street = Suggestion(
                        fiasId = "fiasId",
                        street = "street"
                    ),
                    house = "house",
                    flat = "flat",
                    entrance = "entrance",
                    comment = "comment",
                    floor = "floor",
                )
            }
        )
    }

    @Test
    fun `return userAddress when all data is ok`() = runTest {
        coEvery { dataStoreRepo.getToken() } returns "token"
        coEvery { dataStoreRepo.getSelectedCityUuid() } returns "cityUuid"
        val userAddress = UserAddress(
            uuid = "uuid",
            street = "street",
            house = "house",
            flat = "flat",
            entrance = "entrance",
            comment = "comment",
            floor = "floor",
            userUuid = "userUuid"
        )

        coEvery {
            userAddressRepo.saveUserAddress(
                token = "token",
                createdUserAddress = CreatedUserAddress(
                    street = Suggestion(
                        fiasId = "fiasId",
                        street = "street"
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
        } returns userAddress

        val createdUserAddress = createAddressUseCase(
            street = Suggestion(
                fiasId = "fiasId",
                street = "street"
            ),
            house = "house",
            flat = "flat",
            entrance = "entrance",
            comment = "comment",
            floor = "floor",
        )

        assertEquals(userAddress, createdUserAddress)
    }
}