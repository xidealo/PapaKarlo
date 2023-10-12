package com.bunbeauty.shared.di.usecase

import com.bunbeauty.shared.domain.feature.auth.CheckCodeUseCase
import com.bunbeauty.shared.domain.feature.auth.CheckPhoneNumberUseCase
import com.bunbeauty.shared.domain.feature.auth.FormatPhoneNumberUseCase
import com.bunbeauty.shared.domain.feature.auth.GetPhoneNumberCursorPositionUseCase
import com.bunbeauty.shared.domain.feature.auth.RequestCodeUseCase
import com.bunbeauty.shared.domain.feature.auth.ResendCodeUseCase
import org.koin.dsl.module

internal fun authUseCaseModule() = module {
    factory {
        ResendCodeUseCase(
            authRepo = get(),
        )
    }
    factory {
        CheckCodeUseCase(
            authRepo = get(),
            dataStoreRepo = get(),
        )
    }
    factory {
        RequestCodeUseCase(
            authRepo = get(),
        )
    }
    factory {
        FormatPhoneNumberUseCase()
    }
    factory {
        GetPhoneNumberCursorPositionUseCase(
            formatPhoneNumber = get(),
        )
    }
    factory {
        CheckPhoneNumberUseCase()
    }
}