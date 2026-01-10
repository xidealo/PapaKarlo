package com.bunbeauty.shared.di

import com.bunbeauty.core.domain.order.LightOrderMapper
import com.bunbeauty.shared.data.mapper.user_address.UserAddressMapper
import org.koin.dsl.module

internal fun domainMapperModule() =
    module {
        factory {
            LightOrderMapper()
        }
        factory {
            UserAddressMapper()
        }
    }
