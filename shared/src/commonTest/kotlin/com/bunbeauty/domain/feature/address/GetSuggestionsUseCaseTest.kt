package com.bunbeauty.domain.feature.address

import com.bunbeauty.core.domain.address.GetSuggestionsUseCase
import com.bunbeauty.core.domain.repo.SuggestionRepo
import com.bunbeauty.core.model.Suggestion
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetSuggestionsUseCaseTest {

    private val suggestionRepo: SuggestionRepo = mock()

    private val getSuggestionsUseCase: GetSuggestionsUseCase =
        GetSuggestionsUseCase(
            suggestionRepo = suggestionRepo,
        )

    @Test
    fun `return suggestion list when all data is ok`() =
        runTest {

            val query = "ул Киро"
            val suggestionList =
                listOf(
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
            everySuspend {
                suggestionRepo.getSuggestionList(
                    query = query,
                )
            } returns suggestionList

            val result = getSuggestionsUseCase(query = query)

            assertEquals(suggestionList, result)
        }
}
