package com.bunbeauty.shared.di.usecase

import com.bunbeauty.shared.domain.feature.cafe.GetCafeListUseCase
import com.bunbeauty.shared.domain.feature.cafe.GetSelectableCafeListUseCase
import com.bunbeauty.shared.domain.feature.cafe.ObserveCafeListUseCase
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
    factory {
        ObserveCafeListUseCase(
            getSelectedCityTimeZoneUseCase = get(),
            dataTimeUtil = get(),
            getCafeListUseCase = get(),
        )
    }

}