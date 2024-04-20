package com.bunbeauty.shared.domain.feature.address

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.exeptions.DataNotFoundException
import com.bunbeauty.shared.domain.exeptions.NoSelectedCityUuidException
import com.bunbeauty.shared.domain.exeptions.NoTokenException
import com.bunbeauty.shared.domain.model.Suggestion
import com.bunbeauty.shared.domain.repo.SuggestionRepo

class GetSuggestionsUseCase(
    private val suggestionRepo: SuggestionRepo,
    private val dataStoreRepo: DataStoreRepo,
) {

    suspend operator fun invoke(query: String): List<Suggestion> {
        val token = dataStoreRepo.getToken() ?: throw NoTokenException()
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: throw NoSelectedCityUuidException()

        return suggestionRepo.getSuggestionList(
            token = token,
            query = query,
            cityUuid = cityUuid
        ) ?: throw DataNotFoundException()
    }
}