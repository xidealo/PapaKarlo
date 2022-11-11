package com.bunbeauty.shared.di

import com.bunbeauty.shared.domain.use_case.DisableUserUseCase
import org.koin.dsl.module

fun useCaseModule() = module {
    factory {
        DisableUserUseCase(
            userRepo = get(),
            dataStoreRepo = get(),
        )
    }
}