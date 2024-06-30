package com.bunbeauty.shared.domain.feature.address.di

import com.bunbeauty.shared.domain.feature.address.GetCurrentUserAddressFlowUseCase
import com.bunbeauty.shared.domain.feature.address.GetCurrentUserAddressUseCase
import com.bunbeauty.shared.domain.feature.address.GetSuggestionsUseCase
import org.koin.dsl.module

internal fun addressModule() = module {
    factory {
        GetSuggestionsUseCase(
            suggestionRepo = get(),
            dataStoreRepo = get(),
        )
    }
    factory {
        GetCurrentUserAddressUseCase(
            dataStoreRepo = get(),
            userAddressRepo = get(),
        )
    }
    factory {
        GetCurrentUserAddressFlowUseCase(
            dataStoreRepo = get(),
            userAddressRepo = get(),
        )
    }
}