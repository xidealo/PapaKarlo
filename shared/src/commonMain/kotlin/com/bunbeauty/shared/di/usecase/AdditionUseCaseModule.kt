package com.bunbeauty.shared.di.usecase

import com.bunbeauty.shared.domain.feature.addition.GetAdditionPriorityUseCase
import com.bunbeauty.shared.domain.feature.addition.GetIsAdditionsAreEqualUseCase
import org.koin.dsl.module

internal fun additionUseCaseModule() = module {
    factory {
        GetIsAdditionsAreEqualUseCase()
    }
    factory {
        GetAdditionPriorityUseCase()
    }
}