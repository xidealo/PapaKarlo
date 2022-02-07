package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.papakarlo.feature.auth.phone_verification.IPhoneVerificationUtil
import com.bunbeauty.papakarlo.feature.auth.phone_verification.PhoneVerificationUtil
import com.bunbeauty.papakarlo.feature.main.network.INetworkUtil
import com.bunbeauty.papakarlo.feature.main.network.NetworkUtil
import com.bunbeauty.papakarlo.util.color.ColorUtil
import com.bunbeauty.papakarlo.util.color.IColorUtil
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.util.resources.ResourcesProvider
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.papakarlo.util.string.StringUtil
import com.bunbeauty.papakarlo.util.text_validator.ITextValidator
import com.bunbeauty.papakarlo.util.text_validator.TextValidator
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun appUtilModule() = module {
    single<IResourcesProvider> { ResourcesProvider(androidContext()) }
    single<ITextValidator> { TextValidator() }

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
