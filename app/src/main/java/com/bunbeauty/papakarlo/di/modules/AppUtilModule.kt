package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.papakarlo.network.INetworkUtil
import com.bunbeauty.papakarlo.network.NetworkUtil
import com.bunbeauty.papakarlo.phone_verification.IPhoneVerificationUtil
import com.bunbeauty.papakarlo.phone_verification.PhoneVerificationUtil
import dagger.Binds
import dagger.Module
import org.koin.dsl.module

@Module
interface AppUtilModule {

    @Binds
    fun bindPhoneVerificationUtil(phoneVerificationUtil: PhoneVerificationUtil): IPhoneVerificationUtil

    @Binds
    fun bindNetworkUtil(networkUtil: NetworkUtil): INetworkUtil
}

fun appUtilModule() = module {
    single<IPhoneVerificationUtil> { PhoneVerificationUtil() }
    single<INetworkUtil> {
        NetworkUtil(
            connectivityManager = get()
        )
    }
}