package com.bunbeauty.shared.di.usecase


import com.bunbeauty.core.domain.auth.CheckCodeUseCase
import com.bunbeauty.core.domain.auth.CheckPhoneNumberUseCase
import com.bunbeauty.core.domain.auth.FormatPhoneNumberUseCase
import com.bunbeauty.core.domain.auth.FormatPhoneNumberUseCaseImpl
import com.bunbeauty.core.domain.auth.GetPhoneNumberCursorPositionUseCase
import com.bunbeauty.core.domain.auth.RequestCodeUseCase
import com.bunbeauty.core.domain.auth.ResendCodeUseCase
import org.koin.dsl.module

internal fun authUseCaseModule() =
    module {
        factory {
            ResendCodeUseCase(
                authRepo = get(),
            )
        }
        factory {
            CheckCodeUseCase(
                authRepo = get(),
            )
        }
        factory {
            RequestCodeUseCase(
                authRepo = get(),
            )
        }
        factory<FormatPhoneNumberUseCase> {
            FormatPhoneNumberUseCaseImpl()
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
