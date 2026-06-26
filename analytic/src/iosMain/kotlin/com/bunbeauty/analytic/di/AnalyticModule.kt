package com.bunbeauty.analytic.di

import cocoapods.FirebaseAnalytics.FIRAnalytics
import com.bunbeauty.analytic.AnalyticService
import com.bunbeauty.core.isDebugQualifier
import org.koin.dsl.module

actual fun analyticModule() =
    module {
        single(createdAtStart = true) {
            val isDebug: Boolean = get(isDebugQualifier)
            FIRAnalytics.setAnalyticsCollectionEnabled(!isDebug)
        }
        factory {
            AnalyticService()
        }
    }
