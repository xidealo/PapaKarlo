package com.bunbeauty.shared.di.usecase

import com.bunbeauty.shared.domain.feature.city.GetCityListUseCase
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityUseCase
import com.bunbeauty.shared.domain.feature.city.ObserveSelectedCityUseCase
import com.bunbeauty.shared.domain.feature.city.SaveSelectedCityUseCase
import org.koin.dsl.module

internal fun cityUseCaseModule() = module {
    factory {
        GetCityListUseCase(
            cityRepo = get()
        )
    }
    factory {
        GetSelectedCityUseCase(
            cityRepo = get(),
            dataStoreRepo = get()
        )
    }
    factory {
        ObserveSelectedCityUseCase(
            cityRepo = get(),
            dataStoreRepo = get()
        )
    }
    factory {
        SaveSelectedCityUseCase(
            dataStoreRepo = get()
        )
    }
}
