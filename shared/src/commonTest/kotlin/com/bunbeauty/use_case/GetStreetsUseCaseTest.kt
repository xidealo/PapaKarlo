package com.bunbeauty.use_case

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.exeptions.NoSelectedCityUuidException
import com.bunbeauty.shared.domain.exeptions.NoUserUuidException
import com.bunbeauty.shared.domain.use_case.street.GetStreetsUseCase
import com.bunbeauty.shared.domain.model.street.Street
import com.bunbeauty.shared.domain.repo.StreetRepo
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
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetStreetsUseCaseTest {

    @MockK
    private lateinit var streetRepo: StreetRepo

    @MockK(relaxed = true)
    private lateinit var dataStoreRepo: DataStoreRepo

    @InjectMockKs
    private lateinit var getStreetsUseCase: GetStreetsUseCase

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `return list of streets when has user uuid and selected city uuid`() = runTest {
        coEvery { streetRepo.getStreetList(any(), any()) } returns testStreetList
        assertTrue(getStreetsUseCase().isNotEmpty())
    }

    @Test
    fun `return NoUserUuidException when user has no uuid`() = runTest {
        coEvery { dataStoreRepo.getUserUuid() } returns null
        coEvery { streetRepo.getStreetList(any(), any()) } returns testStreetList

        assertFailsWith(
            exceptionClass = NoUserUuidException::class,
            block = { getStreetsUseCase() }
        )
    }

    @Test
    fun `return NoSelectedCityUuidException when city is not selected has no uuid`() = runTest {
        coEvery { dataStoreRepo.getSelectedCityUuid() } returns null
        coEvery { streetRepo.getStreetList(any(), any()) } returns testStreetList

        assertFailsWith(
            exceptionClass = NoSelectedCityUuidException::class,
            block = { getStreetsUseCase() }
        )
    }

    companion object {
        val testStreetList =
            listOf(
                Street(
                    uuid = "uuid",
                    name = "name",
                    cityUuid = "cityUuid",
                ),
                Street(
                    uuid = "uuid2",
                    name = "name",
                    cityUuid = "cityUuid",
                ),
                Street(
                    uuid = "uuid3",
                    name = "name",
                    cityUuid = "cityUuid",
                ),
            )
    }
}