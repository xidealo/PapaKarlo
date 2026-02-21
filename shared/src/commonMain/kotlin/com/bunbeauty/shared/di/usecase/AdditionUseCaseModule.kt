package com.bunbeauty.shared.di.usecase

import com.bunbeauty.core.domain.GetCartProductAdditionsPriceUseCase
import com.bunbeauty.core.domain.GetCartProductAdditionsPriceUseCaseImpl
import com.bunbeauty.core.domain.addition.AreAdditionsEqualUseCase
import com.bunbeauty.core.domain.addition.AreAdditionsEqualUseCaseImpl
import com.bunbeauty.core.domain.addition.GetAdditionGroupsWithSelectedAdditionUseCase
import com.bunbeauty.core.domain.addition.GetAdditionPriorityUseCase
import com.bunbeauty.core.domain.addition.GetAdditionPriorityUseCaseImpl
import com.bunbeauty.core.domain.addition.GetPriceOfSelectedAdditionsUseCase
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
