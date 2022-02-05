package com.bunbeauty.domain.di

import com.bunbeauty.domain.util.DateTimeUtil
import com.bunbeauty.domain.util.IDateTimeUtil
import dagger.Binds
import dagger.Module
import org.koin.dsl.module

@Module
interface UtilModule {

    @Binds
    fun bindsDateTimeUtil(dateTimeUtil: DateTimeUtil): IDateTimeUtil

}

fun utilModule() = module {
    single<IDateTimeUtil> {
        DateTimeUtil()
    }
}