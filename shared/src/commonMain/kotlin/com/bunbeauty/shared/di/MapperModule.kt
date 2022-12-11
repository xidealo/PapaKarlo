package com.bunbeauty.shared.di

import com.bunbeauty.shared.domain.feature.order.LightOrderMapper
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.util.DateTimeUtil
import com.bunbeauty.shared.domain.util.IDateTimeUtil
import org.koin.dsl.module

internal fun domainMapperModule() = module {
    factory {
        LightOrderMapper()
    }
}