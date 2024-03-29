package com.bunbeauty.shared.domain.feature.address.di

import com.bunbeauty.shared.domain.feature.address.GetSuggestionsUseCase
import org.koin.dsl.module

internal fun addressModule() = module {
    factory {
        GetSuggestionsUseCase(
            suggestionRepo = get(),
            dataStoreRepo = get(),
        )
    }
}