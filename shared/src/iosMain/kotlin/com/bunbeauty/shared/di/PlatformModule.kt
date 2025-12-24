package com.bunbeauty.shared.di

import com.bunbeauty.core.buildVersionQualifier
import com.bunbeauty.core.flavorQualifier
import com.bunbeauty.core.isDebugQualifier
import com.bunbeauty.core.targetName
import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.NetworkUtil
import com.bunbeauty.shared.OpenExternalSource
import com.bunbeauty.shared.data.DataStoreRepository
import com.bunbeauty.shared.data.DatabaseDriverFactory
import com.bunbeauty.shared.data.UuidGenerator
import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.domain.feature.link.GetLinkListUseCase
import com.bunbeauty.shared.domain.feature.notification.UpdateNotificationUseCase
import org.koin.dsl.module
import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
actual fun platformModule() =
    module {
        single {
            FoodDeliveryDatabase(DatabaseDriverFactory().createDriver())
        }
        single<DataStoreRepo> {
            DataStoreRepository()
        }
        single {
            UuidGenerator()
        }
        factory {
            UpdateNotificationUseCase(
                userRepository = get(),
            )
        }
        single(flavorQualifier) { targetName }
        single { NetworkUtil() }
        factory { OpenExternalSource() }
        single(isDebugQualifier) { Platform.isDebugBinary }
        single(buildVersionQualifier) {
            platform.Foundation.NSBundle.mainBundle
                .infoDictionary
                ?.get("CFBundleVersion")
                .toString()
                .toLong()
        }
        factory {
            GetLinkListUseCase(
                linkRepo = get(),
            )
        }
    }
