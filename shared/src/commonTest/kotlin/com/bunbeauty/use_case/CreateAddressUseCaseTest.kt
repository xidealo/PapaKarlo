package com.bunbeauty.use_case

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.exeptions.NoSelectedCityUuidException
import com.bunbeauty.shared.domain.exeptions.NoStreetByNameAndCityUuidException
import com.bunbeauty.shared.domain.exeptions.NoTokenException
import com.bunbeauty.shared.domain.interactor.address.CreateAddressUseCase
import com.bunbeauty.shared.domain.model.address.CreatedUserAddress
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.model.street.Street
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
class CreateAddressUseCaseTest {

    @MockK(relaxed = true)
    private lateinit var dataStoreRepo: DataStoreRepo

    @MockK(relaxed = true)
    private lateinit var streetRepo: StreetRepo

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
                    streetName = "streetName",
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
                    streetName = "streetName",
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
    fun `return NoStreetByNameAndCityUuidException when no street by name and city`() = runTest {
        coEvery { streetRepo.getStreetByNameAndCityUuid(any(), any()) } returns null

        assertFailsWith(
            exceptionClass = NoStreetByNameAndCityUuidException::class,
            block = {
                createAddressUseCase(
                    streetName = "streetName",
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

        coEvery { streetRepo.getStreetByNameAndCityUuid(any(), any()) } returns Street(
            uuid = "uuid",
            name = "streetName",
            cityUuid = "cityUuid ",
        )

        val userAddress = UserAddress(
            uuid = "uuid",
            street = Street(
                uuid = "uuid",
                name = "streetName",
                cityUuid = "cityUuid ",
            ),
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
                    streetUuid = "uuid",
                    house = "house",
                    flat = "flat",
                    entrance = "entrance",
                    comment = "comment",
                    floor = "floor",
                    isVisible = true
                )
            )
        } returns userAddress

        assertEquals(
            userAddress,
            createAddressUseCase(
                streetName = "streetName",
                house = "house",
                flat = "flat",
                entrance = "entrance",
                comment = "comment",
                floor = "floor",
            )
        )
    }
}