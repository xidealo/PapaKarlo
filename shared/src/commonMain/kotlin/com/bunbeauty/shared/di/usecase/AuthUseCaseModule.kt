package com.bunbeauty.shared.di.usecase

import com.bunbeauty.shared.domain.feature.login.FormatPhoneNumberUseCase
import com.bunbeauty.shared.domain.feature.login.GetPhoneNumberCursorPositionUseCase
import com.bunbeauty.shared.domain.use_case.auth.CheckCodeUseCase
import com.bunbeauty.shared.domain.use_case.auth.RequestCodeUseCase
import com.bunbeauty.shared.domain.use_case.auth.ResendCodeUseCase
import org.koin.dsl.module

internal fun authUseCaseModule() = module {
    factory {
        RequestCodeUseCase(
            authRepo = get(),
        )
    }
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
        FormatPhoneNumberUseCase()
    }
    factory {
        GetPhoneNumberCursorPositionUseCase(
            formatPhoneNumber = get(),
        )
    }

}