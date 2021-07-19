package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.data.mapper.user.IUserEntityMapper
import com.bunbeauty.data.mapper.user.IUserFirebaseMapper
import com.bunbeauty.data.mapper.user.UserEntityMapper
import com.bunbeauty.data.mapper.user.UserFirebaseMapper
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class MapperModule {

    @Binds
    abstract fun provideUserFirebaseMapper(userFirebaseMapper: UserFirebaseMapper): IUserFirebaseMapper

    @Binds
    abstract fun provideUserEntityMapper(userEntityMapper: UserEntityMapper): IUserEntityMapper
}