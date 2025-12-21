package com.bunbeauty.shared.di

import com.bunbeauty.shared.domain.util.DateTimeUtil
import com.bunbeauty.shared.domain.util.DateTimeUtilImpl
import org.koin.dsl.module

internal fun utilModule() =
    module {
        single<DateTimeUtil> {
            DateTimeUtilImpl()
        }
    }
