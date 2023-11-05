package com.bunbeauty.analytic.di

import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.dsl.module

actual fun analyticModule() = module {
    single {
        FirebaseAnalytics.getInstance(get())
    }
}
