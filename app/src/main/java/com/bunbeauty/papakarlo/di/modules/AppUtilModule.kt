package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.papakarlo.phone_verification.IPhoneVerificationUtil
import com.bunbeauty.papakarlo.phone_verification.PhoneVerificationUtil
import dagger.Binds
import dagger.Module

@Module
interface AppUtilModule {

    @Binds
    fun bindPhoneVerificationUtil(phoneVerificationUtil: PhoneVerificationUtil): IPhoneVerificationUtil
}