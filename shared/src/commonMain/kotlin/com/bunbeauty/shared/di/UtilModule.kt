package com.bunbeauty.shared.di

import com.bunbeauty.shared.domain.util.DateTimeUtil
import com.bunbeauty.shared.domain.util.IDateTimeUtil
import org.koin.dsl.module

fun utilModule() = module {
    single<IDateTimeUtil> {
        DateTimeUtil()
    }
}