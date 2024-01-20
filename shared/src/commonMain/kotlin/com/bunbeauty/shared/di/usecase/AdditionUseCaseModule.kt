package com.bunbeauty.shared.di.usecase

import com.bunbeauty.shared.domain.feature.addition.GetAdditionPriorityUseCase
import com.bunbeauty.shared.domain.feature.addition.AreAdditionsEqualUseCase
import com.bunbeauty.shared.domain.feature.addition.GetCartProductAdditionsPriceUseCase
import org.koin.dsl.module

internal fun additionUseCaseModule() = module {
    factory {
        AreAdditionsEqualUseCase()
    }
    factory {
        GetAdditionPriorityUseCase()
    }
    factory {
        GetCartProductAdditionsPriceUseCase()
    }
}