package com.bunbeauty.use_case

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.interactor.address.CreateAddressUseCase
import com.bunbeauty.shared.domain.interactor.street.GetStreetsUseCase
import com.bunbeauty.shared.domain.repo.StreetRepo
import com.bunbeauty.shared.domain.repo.UserAddressRepo
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import kotlin.test.BeforeTest

@OptIn(ExperimentalCoroutinesApi::class)
class CreateAddressUseCaseTest {

    @MockK
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

}