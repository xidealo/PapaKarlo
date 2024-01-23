package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.Suggestion

interface SuggestionRepo {

    suspend fun getSuggestionList(token: String, query: String, cityUuid: String): List<Suggestion>?

}