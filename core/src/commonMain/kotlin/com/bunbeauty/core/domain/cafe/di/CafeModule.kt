package com.bunbeauty.core.domain.cafe.di

import com.bunbeauty.core.domain.cafe.GetAdditionalUtensilsUseCase
import com.bunbeauty.core.domain.cafe.GetCafeListUseCase
import com.bunbeauty.core.domain.cafe.GetCafeListUseCaseImpl
import com.bunbeauty.core.domain.cafe.GetDeferredTimeHintUseCase
import com.bunbeauty.core.domain.cafe.GetSelectableCafeListUseCase
import com.bunbeauty.core.domain.cafe.GetWorkloadCafeUseCase
import com.bunbeauty.core.domain.cafe.HasOpenedCafeUseCase
import com.bunbeauty.core.domain.cafe.IsDeliveryEnabledFromCafeUseCase
import com.bunbeauty.core.domain.cafe.IsPickupEnabledFromCafeUseCase
import com.bunbeauty.core.domain.cafe.IsPickupEnabledFromCafeUseCaseImpl
import com.bunbeauty.core.domain.cafe.ObserveCafeWithOpenStateListUseCase
import org.koin.dsl.module

fun cafeModule() =
    module {
        factory<GetCafeListUseCase> {
            GetCafeListUseCaseImpl(
                cafeRepo = get(),
            )
        }
        factory {
            GetSelectableCafeListUseCase(
                cafeRepo = get(),
                getCafeListUseCase = get(),
                isPickupEnabledFromCafeUseCaseImpl = get(),
            )
        }
        factory {
            ObserveCafeWithOpenStateListUseCase(
                getSelectedCityTimeZoneUseCase = get(),
                dataTimeUtil = get(),
                getCafeListUseCase = get(),
            )
        }
        factory {
            IsDeliveryEnabledFromCafeUseCase(
                cafeRepo = get(),
            )
        }
        factory<IsPickupEnabledFromCafeUseCase> {
            IsPickupEnabledFromCafeUseCaseImpl(
                cafeRepo = get(),
            )
        }
        factory {
            HasOpenedCafeUseCase(
                isPickupEnabledFromCafeUseCase = get(),
            )
        }
        factory {
            GetWorkloadCafeUseCase(
                cafeRepo = get(),
            )
        }
        factory {
            GetAdditionalUtensilsUseCase(
                cafeRepo = get(),
            )
        }
        factory {
            GetDeferredTimeHintUseCase()
        }
    }
