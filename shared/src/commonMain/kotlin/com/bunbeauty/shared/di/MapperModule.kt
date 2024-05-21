package com.bunbeauty.shared.di

import com.bunbeauty.shared.data.mapper.user_address.UserAddressMapper
import com.bunbeauty.shared.domain.feature.order.LightOrderMapper
import org.koin.dsl.module

internal fun domainMapperModule() = module {
    factory {
        LightOrderMapper()
    }
    factory {
        UserAddressMapper()
    }
}