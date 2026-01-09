package com.bunbeauty.shared.di.usecase

import com.bunbeauty.core.domain.payment.GetPaymentMethodListUseCase
import com.bunbeauty.core.domain.payment.GetSelectablePaymentMethodListUseCase
import com.bunbeauty.core.domain.payment.GetSelectedPaymentMethodUseCase
import com.bunbeauty.core.domain.payment.SavePaymentMethodUseCase
import org.koin.dsl.module

internal fun paymentUseCaseModule() =
    module {
        factory {
            GetPaymentMethodListUseCase(
                paymentRepo = get(),
            )
        }
        factory {
            GetSelectablePaymentMethodListUseCase(
                paymentRepo = get(),
            )
        }
        factory {
            SavePaymentMethodUseCase(
                paymentRepo = get()
            )
        }
        factory {
            GetSelectedPaymentMethodUseCase()
        }
    }
