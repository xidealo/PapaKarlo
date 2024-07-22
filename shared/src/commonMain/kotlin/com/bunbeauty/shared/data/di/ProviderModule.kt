package com.bunbeauty.shared.data.di

import com.bunbeauty.core.flavorQualifier
import com.bunbeauty.core.isDebugQualifier
import com.bunbeauty.shared.data.CompanyUuidProvider
import org.koin.dsl.module

fun providerModule() = module {
    factory {
        CompanyUuidProvider(
            flavor = get(flavorQualifier),
            isDebug = get(isDebugQualifier)
        )
    }
}
