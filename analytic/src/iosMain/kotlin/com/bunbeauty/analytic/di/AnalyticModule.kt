package com.bunbeauty.analytic.di

import com.bunbeauty.analytic.AnalyticService
import org.koin.dsl.module

actual fun analyticModule() = module {
    single {
        AnalyticService()
    }
}
