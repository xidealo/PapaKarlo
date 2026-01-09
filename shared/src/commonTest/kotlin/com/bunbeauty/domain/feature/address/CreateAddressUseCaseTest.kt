package com.bunbeauty.domain.feature.address

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.core.domain.exeptions.NoSelectedCityUuidException
import com.bunbeauty.core.domain.exeptions.NoTokenException
import com.bunbeauty.shared.domain.feature.address.CreateAddressUseCase
import com.bunbeauty.core.model.Suggestion
import com.bunbeauty.core.model.address.CreatedUserAddress
import com.bunbeauty.core.model.address.UserAddress
import com.bunbeauty.shared.domain.repo.UserAddressRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CreateAddressUseCaseTest {
    private val dataStoreRepo = mock<DataStoreRepo>()

    private val userAddressRepo = mock<UserAddressRepo>()

    private val createAddressUseCase: CreateAddressUseCase =
        CreateAddressUseCase(
            dataStoreRepo = dataStoreRepo,
            userAddressRepo = userAddressRepo,
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `return NoTokenException when token is null`() =
        runTest {
            everySuspend { dataStoreRepo.getToken() } returns null

            assertFailsWith(
                exceptionClass = NoTokenException::class,
                block = {
                    createAddressUseCase(
                        street =
                            Suggestion(
                                fiasId = "fiasId",
                                street = "street",
                                details = null,
                            ),
                        house = "house",
                        flat = "flat",
                        entrance = "entrance",
                        comment = "comment",
                        floor = "floor",
                    )
                },
            )
        }

    @Test
    fun `return NoSelectedCityUuidException when selected city uuid is null`() =
        runTest {
            everySuspend { dataStoreRepo.getToken() } returns ""

            everySuspend { dataStoreRepo.getSelectedCityUuid() } returns null

            assertFailsWith(
                exceptionClass = NoSelectedCityUuidException::class,
                block = {
                    createAddressUseCase(
                        street =
                            Suggestion(
                                fiasId = "fiasId",
                                street = "street",
                                details = null,
                            ),
                        house = "house",
                        flat = "flat",
                        entrance = "entrance",
                        comment = "comment",
                        floor = "floor",
                    )
                },
            )
        }

    @Test
    fun `return userAddress when all data is ok`() =
        runTest {
            everySuspend { dataStoreRepo.getToken() }.returns("token")
            everySuspend { dataStoreRepo.getSelectedCityUuid() }.returns("cityUuid")
            val userAddress =
                UserAddress(
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
                    userUuid = "userUuid",
                    cafeUuid = "cafeUuid",
                )

            everySuspend {
                userAddressRepo.saveUserAddress(
                    token = "token",
                    createdUserAddress =
                        CreatedUserAddress(
                            street =
                                Suggestion(
                                    fiasId = "fiasId",
                                    street = "street",
                                    details = null,
                                ),
                            house = "house",
                            flat = "flat",
                            entrance = "entrance",
                            comment = "comment",
                            floor = "floor",
                            cityUuid = "cityUuid",
                            isVisible = true,
                        ),
                )
            }.returns(userAddress)

            everySuspend {
                dataStoreRepo.saveUserCafeUuid(
                    userCafeUuid = "cafeUuid",
                )
            }.returns(Unit)

            val createdUserAddress =
                createAddressUseCase(
                    street =
                        Suggestion(
                            fiasId = "fiasId",
                            street = "street",
                            details = null,
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
