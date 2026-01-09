package com.bunbeauty.shared.data.repository

import com.bunbeauty.core.domain.exeptions.NoSelectedCityUuidException
import com.bunbeauty.core.domain.exeptions.NoTokenException
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.core.model.Suggestion
import com.bunbeauty.core.domain.repo.SuggestionRepo
import com.bunbeauty.core.extension.dataOrNull
import com.bunbeauty.shared.DataStoreRepo

class SuggestionRepository(
    private val networkConnector: NetworkConnector,
    private val dataStoreRepo: DataStoreRepo,
) : SuggestionRepo {
    private val cache: MutableMap<String, List<Suggestion>> = mutableMapOf()

    override suspend fun getSuggestionList(
        query: String,
    ): List<Suggestion>? {
        val token = dataStoreRepo.getToken() ?: throw NoTokenException()
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: throw NoSelectedCityUuidException()

        if (cache[query] != null) {
            return cache[query]
        }

        val suggestions =
            networkConnector
                .getSuggestions(
                    token = token,
                    query = query,
                    cityUuid = cityUuid,
                ).dataOrNull()
                ?.results
                ?.map { suggestionServer ->
                    Suggestion(
                        fiasId = suggestionServer.fiasId,
                        street = suggestionServer.street,
                        details = suggestionServer.details,
                    )
                }
        if (suggestions != null) {
            cache[query] = suggestions
        }

        return suggestions
    }
}
