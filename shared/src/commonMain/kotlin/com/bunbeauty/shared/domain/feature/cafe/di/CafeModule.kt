package com.bunbeauty.shared.domain.feature.cafe.di

import com.bunbeauty.shared.domain.feature.cafe.GetCafeListUseCase
import com.bunbeauty.shared.domain.feature.cafe.GetSelectableCafeListUseCase
import com.bunbeauty.shared.domain.feature.cafe.ObserveCafeWithOpenStateListUseCase
import org.koin.dsl.module

internal fun cafeModule() = module {
    factory {
        GetCafeListUseCase(
            cafeRepo = get(),
            dataStoreRepo = get()
        )
    }
    factory {
        GetSelectableCafeListUseCase(
            cafeRepo = get(),
            dataStoreRepo = get(),
            getCafeListUseCase = get()
        )
    }
    factory {
        ObserveCafeWithOpenStateListUseCase(
            getSelectedCityTimeZoneUseCase = get(),
            dataTimeUtil = get(),
            getCafeListUseCase = get()
        )
    }
}
