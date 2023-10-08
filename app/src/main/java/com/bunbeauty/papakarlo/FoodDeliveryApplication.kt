package com.bunbeauty.papakarlo

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import coil.Coil
import coil.ImageLoader
import com.bunbeauty.papakarlo.di.appModule
import com.bunbeauty.papakarlo.di.appUtilModule
import com.bunbeauty.papakarlo.di.uiMapperModule
import com.bunbeauty.papakarlo.di.viewModelModule
import com.bunbeauty.shared.Constants.CHANNEL_ID
import com.bunbeauty.shared.di.initKoin
import com.bunbeauty.shared.domain.feature.notification.SubscribeToNotificationUseCase
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.logger.Level

private const val NOTIFICATION_NEWS_CHANNEL_NAME = "NEWS"

class FoodDeliveryApplication : Application(), KoinComponent {

    private val subscribeToNotification: SubscribeToNotificationUseCase by inject()

    override fun onCreate() {
        setTheme(R.style.AppTheme)
        super.onCreate()

        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)

        initKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@FoodDeliveryApplication)
            modules(
                appModule(),
                appUtilModule(),
                uiMapperModule(),
                viewModelModule()
            )
        }

        Coil.setImageLoader(
            ImageLoader.Builder(applicationContext)
                .respectCacheHeaders(false)
                .build()
        )

        subscribeToNotification()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }

        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(
            CHANNEL_ID,
            NOTIFICATION_NEWS_CHANNEL_NAME,
            importance
        ).apply {
            enableLights(true)
            enableVibration(true)

            val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            setSound(soundUri, attributes)
        }
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
