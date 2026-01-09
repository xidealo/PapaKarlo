package com.bunbeauty.core.domain.repo

import com.bunbeauty.core.model.Suggestion

interface SuggestionRepo {
    suspend fun getSuggestionList(
        token: String,
        query: String,
        cityUuid: String,
    ): List<Suggestion>?
}
