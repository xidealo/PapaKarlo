package com.bunbeauty.domain.di

import com.bunbeauty.domain.util.DateTimeUtil
import com.bunbeauty.domain.util.IDateTimeUtil
import org.koin.dsl.module

fun utilModule() = module {
    single<IDateTimeUtil> {
        DateTimeUtil()
    }
}