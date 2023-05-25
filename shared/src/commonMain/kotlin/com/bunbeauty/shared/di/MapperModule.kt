package com.bunbeauty.shared.di

import com.bunbeauty.shared.data.mapper.user_address.UserAddressMapper
import com.bunbeauty.shared.domain.feature.order.LightOrderMapper
import com.bunbeauty.shared.presentation.create_order.CreateOrderStateMapper
import com.bunbeauty.shared.presentation.create_order.TimeMapper
import org.koin.dsl.module

internal fun domainMapperModule() = module {
    factory {
        LightOrderMapper()
    }
    single { UserAddressMapper(streetMapper = get()) }
    single {
        TimeMapper()
    }
    single {
        CreateOrderStateMapper(
            userAddressMapper = get(),
            timeMapper = get(),
        )
    }
}