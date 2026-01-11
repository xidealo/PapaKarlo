package com.bunbeauty.shared.di

import com.bunbeauty.core.domain.util.DateTimeUtil
import com.bunbeauty.core.domain.util.DateTimeUtilImpl
import org.koin.dsl.module

internal fun utilModule() =
    module {
        single<DateTimeUtil> {
            DateTimeUtilImpl()
        }
    }
