package com.bunbeauty.core.domain.address.di

import com.bunbeauty.core.domain.address.GetCurrentUserAddressFlowUseCase
import com.bunbeauty.core.domain.address.GetCurrentUserAddressUseCase
import com.bunbeauty.core.domain.address.GetCurrentUserAddressUseCaseImpl
import com.bunbeauty.core.domain.address.GetCurrentUserAddressWithCityUseCase
import com.bunbeauty.core.domain.address.GetCurrentUserAddressWithCityUseCaseImpl
import com.bunbeauty.core.domain.address.GetSuggestionsUseCase
import org.koin.dsl.module

fun addressModule() =
    module {
        factory {
            GetSuggestionsUseCase(
                suggestionRepo = get(),
            )
        }
        factory<GetCurrentUserAddressUseCase> {
            GetCurrentUserAddressUseCaseImpl(
                userAddressRepo = get(),
            )
        }
        factory<GetCurrentUserAddressWithCityUseCase> {
            GetCurrentUserAddressWithCityUseCaseImpl(
                userAddressRepo = get(),
                getSelectedCityUseCase = get(),
            )
        }
        factory {
            GetCurrentUserAddressFlowUseCase(
                userAddressRepo = get(),
            )
        }
    }
