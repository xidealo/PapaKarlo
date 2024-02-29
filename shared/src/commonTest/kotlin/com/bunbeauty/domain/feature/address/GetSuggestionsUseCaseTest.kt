package com.bunbeauty.domain.feature.address

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.exeptions.NoSelectedCityUuidException
import com.bunbeauty.shared.domain.exeptions.NoTokenException
import com.bunbeauty.shared.domain.feature.address.GetSuggestionsUseCase
import com.bunbeauty.shared.domain.model.Suggestion
import com.bunbeauty.shared.domain.repo.SuggestionRepo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetSuggestionsUseCaseTest {

    @MockK(relaxed = true)
    private lateinit var dataStoreRepo: DataStoreRepo

    @MockK(relaxed = true)
    private lateinit var suggestionRepo: SuggestionRepo

    @InjectMockKs
    private lateinit var getSuggestionsUseCase: GetSuggestionsUseCase

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `return NoTokenException when token is null`() = runTest {
        coEvery { dataStoreRepo.getToken() } returns null

        assertFailsWith(
            exceptionClass = NoTokenException::class,
            block = {
                getSuggestionsUseCase(query = "ул Киро")
            }
        )
    }

    @Test
    fun `return NoSelectedCityUuidException when token is null`() = runTest {
        coEvery { dataStoreRepo.getToken() } returns "token"
        coEvery { dataStoreRepo.getSelectedCityUuid() } returns null

        assertFailsWith(
            exceptionClass = NoSelectedCityUuidException::class,
            block = {
                getSuggestionsUseCase(query = "ул Киро")
            }
        )
    }

    @Test
    fun `return suggestion list when all data is ok`() = runTest {
        coEvery { dataStoreRepo.getToken() } returns "token"
        coEvery { dataStoreRepo.getSelectedCityUuid() } returns "cityUuid"

        val query = "ул Киро"
        val suggestionList = listOf(
            Suggestion(
                fiasId = "fiasId1",
                street = "street1",
                details = null,
            ),
            Suggestion(
                fiasId = "fiasId2",
                street = "street2",
                details = null,
            ),
        )
        coEvery {
            suggestionRepo.getSuggestionList(
                token = "token",
                query = query,
                cityUuid = "cityUuid"
            )
        } returns suggestionList

        val result = getSuggestionsUseCase(query = query)

        assertEquals(suggestionList, result)
    }


}