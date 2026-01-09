package com.bunbeauty.core.domain.address

import com.bunbeauty.core.domain.exeptions.DataNotFoundException
import com.bunbeauty.core.model.Suggestion
import com.bunbeauty.core.domain.repo.SuggestionRepo

class GetSuggestionsUseCase(
    private val suggestionRepo: SuggestionRepo,
) {
    suspend operator fun invoke(query: String): List<Suggestion> {
        return suggestionRepo.getSuggestionList(
            query = query,
        ) ?: throw DataNotFoundException()
    }
}
