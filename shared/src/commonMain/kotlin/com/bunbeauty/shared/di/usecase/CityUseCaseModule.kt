package com.bunbeauty.shared.di.usecase

import com.bunbeauty.core.domain.city.GetCityListUseCase
import com.bunbeauty.core.domain.city.GetSelectedCityUseCase
import com.bunbeauty.core.domain.city.GetSelectedCityUseCaseImpl
import com.bunbeauty.core.domain.city.ObserveSelectedCityUseCase
import com.bunbeauty.core.domain.city.SaveSelectedCityUseCase
import org.koin.dsl.module

internal fun cityUseCaseModule() =
    module {
        factory {
            GetCityListUseCase(
                cityRepo = get(),
            )
        }
        factory<GetSelectedCityUseCase> {
            GetSelectedCityUseCaseImpl(
                cityRepo = get(),
            )
        }
        factory {
            ObserveSelectedCityUseCase(
                cityRepo = get(),
            )
        }
        factory {
            SaveSelectedCityUseCase(
                cityRepo = get(),
            )
        }
    }
