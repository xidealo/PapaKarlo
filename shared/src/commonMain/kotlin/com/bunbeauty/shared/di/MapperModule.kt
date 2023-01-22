package com.bunbeauty.shared.di

import com.bunbeauty.shared.domain.feature.order.LightOrderMapper
import com.bunbeauty.shared.presentation.create_order.TimeMapper
import org.koin.dsl.module

internal fun domainMapperModule() = module {
    factory {
        LightOrderMapper()
    }
    single {
        TimeMapper()
    }
}