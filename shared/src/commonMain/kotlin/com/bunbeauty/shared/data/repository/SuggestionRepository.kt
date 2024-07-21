package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.domain.model.Suggestion
import com.bunbeauty.shared.domain.repo.SuggestionRepo
import com.bunbeauty.shared.extension.dataOrNull

class SuggestionRepository(
    private val networkConnector: NetworkConnector
) : SuggestionRepo {

    private val cache: MutableMap<String, List<Suggestion>> = mutableMapOf()

    override suspend fun getSuggestionList(token: String, query: String, cityUuid: String): List<Suggestion>? {
        if (cache[query] != null) {
            return cache[query]
        }

        val suggestions = networkConnector.getSuggestions(
            token = token,
            query = query,
            cityUuid = cityUuid
        ).dataOrNull()?.results?.map { suggestionServer ->
            Suggestion(
                fiasId = suggestionServer.fiasId,
                street = suggestionServer.street,
                details = suggestionServer.details
            )
        }
        if (suggestions != null) {
            cache[query] = suggestions
        }

        return suggestions
    }
}
