package com.bunbeauty.auth.di

import com.bunbeauty.auth.presentation.confirm.ConfirmViewModel
import com.bunbeauty.auth.presentation.login.LoginViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun authFeatureModule() =
    module {
        viewModel {
            LoginViewModel(
                requestCode = get(),
                formatPhoneNumber = get(),
                getPhoneNumberCursorPosition = get(),
                checkPhoneNumber = get(),
            )
        }
        viewModel {
            ConfirmViewModel(
                formatPhoneNumber = get(),
                checkCode = get(),
                resendCode = get(),
                analyticService = get(),
                updateNotificationUseCase = get(),
            )
        }
    }
