package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.papakarlo.phone_verification.IPhoneVerificationUtil
import com.bunbeauty.papakarlo.phone_verification.PhoneVerificationUtil
import dagger.Binds
import dagger.Module
import org.koin.dsl.bind
import org.koin.dsl.module

@Module
interface AppUtilModule {

    @Binds
    fun bindPhoneVerificationUtil(phoneVerificationUtil: PhoneVerificationUtil): IPhoneVerificationUtil
}

fun appUtilModule() = module {
    single { PhoneVerificationUtil() } bind IPhoneVerificationUtil::class
}