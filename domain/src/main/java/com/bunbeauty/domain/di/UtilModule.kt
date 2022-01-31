package com.bunbeauty.domain.di

import com.bunbeauty.domain.util.DateTimeUtil
import com.bunbeauty.domain.util.IDateTimeUtil
import dagger.Binds
import dagger.Module

@Module
interface UtilModule {

    @Binds
    fun bindsDateTimeUtil(dateTimeUtil: DateTimeUtil): IDateTimeUtil

}