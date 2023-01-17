package com.bunbeauty.shared.di

import com.bunbeauty.shared.domain.feature.order.LightOrderMapper
import com.bunbeauty.shared.presentation.create_order.TimeMapper
import com.bunbeauty.shared.presentation.order_details.OrderDetailsMapper
import org.koin.dsl.module

internal fun domainMapperModule() = module {
    factory {
        LightOrderMapper()
    }
    single {
        TimeMapper()
    }
    factory {
        OrderDetailsMapper(
            timeMapper = get()
        )
    }
}