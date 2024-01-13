package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.domain.model.Suggestion
import com.bunbeauty.shared.domain.repo.SuggestionRepo
import com.bunbeauty.shared.extension.dataOrNull

class SuggestionRepository(
    private val networkConnector: NetworkConnector,
) : SuggestionRepo {

    override suspend fun getSuggestionList(token: String, query: String, cityUuid: String): List<Suggestion>? {
        return networkConnector.getSuggestions(
            token = token,
            query = query,
            cityUuid = cityUuid
        ).dataOrNull()?.results?.map { suggestionServer ->
            Suggestion(
                fiasId = suggestionServer.fiasId,
                street = suggestionServer.street,
            )
        }
    }

}