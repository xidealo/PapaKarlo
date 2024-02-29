package com.bunbeauty.shared.di

import com.bunbeauty.shared.data.mapper.user_address.UserAddressMapper
import com.bunbeauty.shared.domain.feature.order.LightOrderMapper
import com.bunbeauty.shared.presentation.create_order.CreateOrderStateMapper
import org.koin.dsl.module

internal fun domainMapperModule() = module {
    factory {
        LightOrderMapper()
    }
    factory {
        UserAddressMapper()
    }
    factory {
        CreateOrderStateMapper()
    }
}