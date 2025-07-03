package com.bunbeauty.shared.domain.feature.address.di

import com.bunbeauty.shared.domain.feature.address.GetCurrentUserAddressFlowUseCase
import com.bunbeauty.shared.domain.feature.address.GetCurrentUserAddressUseCase
import com.bunbeauty.shared.domain.feature.address.GetCurrentUserAddressUseCaseImpl
import com.bunbeauty.shared.domain.feature.address.GetCurrentUserAddressWithCityUseCase
import com.bunbeauty.shared.domain.feature.address.GetCurrentUserAddressWithCityUseCaseImpl
import com.bunbeauty.shared.domain.feature.address.GetSuggestionsUseCase
import org.koin.dsl.module

internal fun addressModule() = module {
    factory {
        GetSuggestionsUseCase(
            suggestionRepo = get(),
            dataStoreRepo = get()
        )
    }
    factory<GetCurrentUserAddressUseCase> {
        GetCurrentUserAddressUseCaseImpl(
            dataStoreRepo = get(),
            userAddressRepo = get()
        )
    }
    factory<GetCurrentUserAddressWithCityUseCase> {
        GetCurrentUserAddressWithCityUseCaseImpl(
            dataStoreRepo = get(),
            userAddressRepo = get(),
            getSelectedCityUseCase = get()
        )
    }
    factory {
        GetCurrentUserAddressFlowUseCase(
            dataStoreRepo = get(),
            userAddressRepo = get()
        )
    }
}
