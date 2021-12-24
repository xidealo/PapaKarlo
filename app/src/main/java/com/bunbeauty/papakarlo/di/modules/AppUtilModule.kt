package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.papakarlo.network.INetworkUtil
import com.bunbeauty.papakarlo.network.NetworkUtil
import com.bunbeauty.papakarlo.phone_verification.IPhoneVerificationUtil
import com.bunbeauty.papakarlo.phone_verification.PhoneVerificationUtil
import com.bunbeauty.presentation.util.color.ColorUtil
import com.bunbeauty.presentation.util.color.IColorUtil
import com.bunbeauty.presentation.util.string.IStringUtil
import com.bunbeauty.presentation.util.string.StringUtil
import dagger.Binds
import dagger.Module
import org.koin.dsl.module

@Module
interface AppUtilModule {

    @Binds
    fun bindPhoneVerificationUtil(phoneVerificationUtil: PhoneVerificationUtil): IPhoneVerificationUtil

    @Binds
    fun bindNetworkUtil(networkUtil: NetworkUtil): INetworkUtil

    @Binds
    fun bindStringUtil(stringHelper: StringUtil): IStringUtil

    @Binds
    fun bindColorUtil(colorUtil: ColorUtil): IColorUtil
}

fun appUtilModule() = module {
    single<IPhoneVerificationUtil> { PhoneVerificationUtil() }
    single<INetworkUtil> {
        NetworkUtil(
            connectivityManager = get()
        )
    }
    single<IStringUtil> {
        StringUtil(
            resourcesProvider = get(),
        )
    }
    single<IColorUtil> { ColorUtil() }
}