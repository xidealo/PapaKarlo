package com.bunbeauty.domain.feature.address

import com.bunbeauty.core.domain.address.CreateAddressUseCase
import com.bunbeauty.core.domain.repo.UserAddressRepo
import com.bunbeauty.core.domain.repo.UserRepo
import com.bunbeauty.core.model.Suggestion
import com.bunbeauty.core.model.address.CreatedUserAddress
import com.bunbeauty.core.model.address.UserAddress
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CreateAddressUseCaseTest {
    private val userAddressRepo = mock<UserAddressRepo>()
    private val userRepo = mock<UserRepo>()

    private val createAddressUseCase: CreateAddressUseCase =
        CreateAddressUseCase(
            userAddressRepo = userAddressRepo,
            userRepo = userRepo,
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `return userAddress when all data is ok`() =
        runTest {
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
                            isVisible = true,
                        ),
                )
            }.returns(userAddress)

            everySuspend {
                userRepo.saveUserCafeUuid(
                    "cafeUuid",
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
            verifySuspend(mode = VerifyMode.atLeast(0)) {
                userRepo.saveUserCafeUuid(
                    "cafeUuid",
                )
            }
        }
}
