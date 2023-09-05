package com.bunbeauty.shared.di.usecase

import com.bunbeauty.shared.domain.use_case.cafe.GetCafeListUseCase
import com.bunbeauty.shared.domain.use_case.cafe.GetSelectableCafeListUseCase
import org.koin.dsl.module

internal fun cafeUseCaseModule() = module {
    factory {
        GetCafeListUseCase(
            cafeRepo = get(),
            dataStoreRepo = get(),
        )
    }
    factory {
        GetSelectableCafeListUseCase(
            cafeRepo = get(),
            dataStoreRepo = get(),
        )
    }

}