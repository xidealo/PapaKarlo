package com.bunbeauty.shared.domain.feature.cafe.di

import com.bunbeauty.shared.domain.feature.cafe.GetCafeListUseCase
import com.bunbeauty.shared.domain.feature.cafe.GetCafeListUseCaseImpl
import com.bunbeauty.shared.domain.feature.cafe.GetSelectableCafeListUseCase
import com.bunbeauty.shared.domain.feature.cafe.GetWorkloadCafeUseCase
import com.bunbeauty.shared.domain.feature.cafe.HasOpenedCafeUseCase
import com.bunbeauty.shared.domain.feature.cafe.IsDeliveryEnabledFromCafeUseCase
import com.bunbeauty.shared.domain.feature.cafe.IsPickupEnabledFromCafeUseCase
import com.bunbeauty.shared.domain.feature.cafe.IsPickupEnabledFromCafeUseCaseImpl
import com.bunbeauty.shared.domain.feature.cafe.ObserveCafeWithOpenStateListUseCase
import org.koin.dsl.module

internal fun cafeModule() = module {
    factory<GetCafeListUseCase> {
        GetCafeListUseCaseImpl(
            cafeRepo = get(),
            dataStoreRepo = get()
        )
    }
    factory {
        GetSelectableCafeListUseCase(
            cafeRepo = get(),
            dataStoreRepo = get(),
            getCafeListUseCase = get(),
            isPickupEnabledFromCafeUseCaseImpl = get()
        )
    }
    factory {
        ObserveCafeWithOpenStateListUseCase(
            getSelectedCityTimeZoneUseCase = get(),
            dataTimeUtil = get(),
            getCafeListUseCase = get()
        )
    }
    factory {
        IsDeliveryEnabledFromCafeUseCase(
            cafeRepo = get()
        )
    }
    factory<IsPickupEnabledFromCafeUseCase> {
        IsPickupEnabledFromCafeUseCaseImpl(
            cafeRepo = get()
        )
    }
    factory {
        HasOpenedCafeUseCase(
            isPickupEnabledFromCafeUseCase = get()
        )
    }
    factory {
        GetWorkloadCafeUseCase(
            cafeRepo = get()
        )
    }
}
