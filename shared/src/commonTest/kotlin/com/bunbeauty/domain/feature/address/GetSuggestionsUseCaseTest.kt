package com.bunbeauty.domain.feature.address

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.exeptions.NoSelectedCityUuidException
import com.bunbeauty.shared.domain.exeptions.NoTokenException
import com.bunbeauty.shared.domain.feature.address.GetSuggestionsUseCase
import com.bunbeauty.shared.domain.model.Suggestion
import com.bunbeauty.shared.domain.repo.SuggestionRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetSuggestionsUseCaseTest {

    private val dataStoreRepo: DataStoreRepo = mock()

    private val suggestionRepo: SuggestionRepo = mock()

    private val getSuggestionsUseCase: GetSuggestionsUseCase = GetSuggestionsUseCase(
        suggestionRepo = suggestionRepo,
        dataStoreRepo = dataStoreRepo
    )

    @Test
    fun `return NoTokenException when token is null`() = runTest {
        everySuspend { dataStoreRepo.getToken() } returns null

        assertFailsWith(
            exceptionClass = NoTokenException::class,
            block = {
                getSuggestionsUseCase(query = "ул Киро")
            }
        )
    }

    @Test
    fun `return NoSelectedCityUuidException when token is null`() = runTest {
        everySuspend { dataStoreRepo.getToken() } returns "token"
        everySuspend { dataStoreRepo.getSelectedCityUuid() } returns null

        assertFailsWith(
            exceptionClass = NoSelectedCityUuidException::class,
            block = {
                getSuggestionsUseCase(query = "ул Киро")
            }
        )
    }

    @Test
    fun `return suggestion list when all data is ok`() = runTest {
        everySuspend { dataStoreRepo.getToken() } returns "token"
        everySuspend { dataStoreRepo.getSelectedCityUuid() } returns "cityUuid"

        val query = "ул Киро"
        val suggestionList = listOf(
            Suggestion(
                fiasId = "fiasId1",
                street = "street1",
                details = null
            ),
            Suggestion(
                fiasId = "fiasId2",
                street = "street2",
                details = null
            )
        )
        everySuspend {
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
