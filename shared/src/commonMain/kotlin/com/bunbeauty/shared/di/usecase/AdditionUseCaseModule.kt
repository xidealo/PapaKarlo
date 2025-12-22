package com.bunbeauty.shared.di.usecase

import com.bunbeauty.shared.domain.feature.addition.AreAdditionsEqualUseCase
import com.bunbeauty.shared.domain.feature.addition.AreAdditionsEqualUseCaseImpl
import com.bunbeauty.shared.domain.feature.addition.GetAdditionGroupsWithSelectedAdditionUseCase
import com.bunbeauty.shared.domain.feature.addition.GetAdditionPriorityUseCase
import com.bunbeauty.shared.domain.feature.addition.GetAdditionPriorityUseCaseImpl
import com.bunbeauty.shared.domain.feature.addition.GetCartProductAdditionsPriceUseCase
import com.bunbeauty.shared.domain.feature.addition.GetCartProductAdditionsPriceUseCaseImpl
import com.bunbeauty.shared.domain.feature.addition.GetPriceOfSelectedAdditionsUseCase
import org.koin.dsl.module

internal fun additionUseCaseModule() =
    module {
        factory<AreAdditionsEqualUseCase> {
            AreAdditionsEqualUseCaseImpl()
        }
        factory<GetAdditionPriorityUseCase> {
            GetAdditionPriorityUseCaseImpl()
        }
        factory<GetCartProductAdditionsPriceUseCase> {
            GetCartProductAdditionsPriceUseCaseImpl()
        }
        factory {
            GetPriceOfSelectedAdditionsUseCase()
        }
        factory {
            GetAdditionGroupsWithSelectedAdditionUseCase()
        }
    }
