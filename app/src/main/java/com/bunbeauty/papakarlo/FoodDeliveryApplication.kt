package com.bunbeauty.papakarlo

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import com.bunbeauty.core.Logger
import com.bunbeauty.papakarlo.di.appModule
import com.bunbeauty.papakarlo.di.appUtilModule
import com.bunbeauty.papakarlo.di.uiMapperModule
import com.bunbeauty.papakarlo.di.viewModelModule
import com.bunbeauty.shared.Constants.CHANNEL_ID
import com.bunbeauty.shared.data.CompanyUuidProvider
import com.bunbeauty.shared.di.initKoin
import com.bunbeauty.shared.domain.feature.notification.SubscribeToNotificationUseCase
import com.bunbeauty.shared.domain.feature.notification.UpdateNotificationUseCase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.logger.Level
import kotlin.coroutines.CoroutineContext

private const val NOTIFICATION_NEWS_CHANNEL_NAME = "NEWS"
private const val FOOD_DELIVERY_APPLICATION_TAG = "FoodDeliveryApplication"

class FoodDeliveryApplication :
    Application(),
    KoinComponent,
    CoroutineScope {
    override val coroutineContext: CoroutineContext = SupervisorJob()

    private val subscribeToNotification: SubscribeToNotificationUseCase by inject()
    private val companyUuidProvider: CompanyUuidProvider by inject()
    private val updateNotificationUseCase: UpdateNotificationUseCase by inject()

    override fun onCreate() {
        setTheme(R.style.AppTheme)
        super.onCreate()

        initKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@FoodDeliveryApplication)
            modules(
                appModule(),
                appUtilModule(),
                uiMapperModule(),
                viewModelModule(),
            )
        }

        FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = !BuildConfig.DEBUG
        FirebaseAnalytics
            .getInstance(applicationContext)
            .setAnalyticsCollectionEnabled(!BuildConfig.DEBUG)
        subscribeToNotification(companyUuid = companyUuidProvider.companyUuid)
        createNotificationChannel()
        updateNotificationToken()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }

        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                NOTIFICATION_NEWS_CHANNEL_NAME,
                importance,
            ).apply {
                enableLights(true)
                enableVibration(true)

                val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val attributes =
                    AudioAttributes
                        .Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build()
                setSound(soundUri, attributes)
            }
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun updateNotificationToken() {
        launch {
            try {
                updateNotificationUseCase()
            } catch (e: Exception) {
                Logger.logE(
                    FOOD_DELIVERY_APPLICATION_TAG,
                    "Not updateNotificationToken cause: ${e.javaClass.name} ",
                )
            }
        }
    }
}
