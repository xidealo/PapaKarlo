package com.bunbeauty.shared.di

import android.content.Context
import android.content.pm.PackageInfo
import android.os.Build
import com.bunbeauty.auth.domain.UpdateNotificationUseCase
import com.bunbeauty.core.buildVersionQualifier
import com.bunbeauty.core.domain.link.GetLinkListUseCase
import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.NetworkUtil
import com.bunbeauty.shared.OpenExternalSource
import com.bunbeauty.shared.data.DataStoreRepository
import com.bunbeauty.shared.data.DatabaseDriverFactory
import com.bunbeauty.shared.data.UuidGenerator
import com.bunbeauty.shared.db.FoodDeliveryDatabase
import org.koin.dsl.module

actual fun platformModule() =
    module {
        single {
            val driver =
                DatabaseDriverFactory(context = get())
                    .createDriver()
            FoodDeliveryDatabase(driver)
        }
        single<DataStoreRepo> {
            DataStoreRepository()
        }
        single {
            UuidGenerator()
        }
        single(buildVersionQualifier) {
            val context = get<Context>()
            val pInfo: PackageInfo =
                context.packageManager.getPackageInfo(context.packageName, 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                pInfo.longVersionCode
            } else {
                pInfo.versionCode.toLong()
            }
        }
        factory {
            UpdateNotificationUseCase(
                userRepository = get(),
            )
        }

        single {
            OpenExternalSource(
                context = get(),
            )
        }
        single {
            NetworkUtil(
                connectivityManager = get(),
            )
        }
        factory {
            GetLinkListUseCase(
                linkRepo = get(),
            )
        }
    }
