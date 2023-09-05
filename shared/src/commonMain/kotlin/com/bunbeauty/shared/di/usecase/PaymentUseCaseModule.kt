package com.bunbeauty.shared.di.usecase

import com.bunbeauty.shared.domain.feature.payment.GetPaymentMethodListUseCase
import com.bunbeauty.shared.domain.feature.payment.GetSelectablePaymentMethodListUseCase
import com.bunbeauty.shared.domain.feature.payment.SavePaymentMethodUseCase
import org.koin.dsl.module

internal fun paymentUseCaseModule() = module {
    factory {
        GetPaymentMethodListUseCase(
            paymentRepo = get()
        )
    }
    factory {
        GetSelectablePaymentMethodListUseCase(
            paymentRepo = get(),
            dataStoreRepo = get()
        )
    }
    factory {
        SavePaymentMethodUseCase(
            dataStoreRepo = get()
        )
    }
}