package com.bunbeauty.papakarlo

import android.app.Application
import androidx.viewbinding.BuildConfig
import coil.Coil
import coil.ImageLoader
import com.bunbeauty.data.di.databaseModule
import com.bunbeauty.data.di.mapperModule
import com.bunbeauty.data.di.networkModule
import com.bunbeauty.data.di.repositoryModule
import com.bunbeauty.domain.di.interactorModule
import com.bunbeauty.domain.di.utilModule
import com.bunbeauty.papakarlo.di.modules.*
import com.bunbeauty.shared.AppInfo
import com.bunbeauty.shared.di.initKoin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.logger.Level
import kotlin.coroutines.CoroutineContext

class PapaKarloApplication : Application(), CoroutineScope, KoinComponent {

    override val coroutineContext: CoroutineContext = Job()

    override fun onCreate() {
        setTheme(R.style.AppTheme)
        super.onCreate()

        initKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@PapaKarloApplication)
            modules(
                appModule(),
                appUtilModule(),
                uiMapperModule(),
                viewModelModule(),
                databaseModule(),
                networkModule(),
                mapperModule(),
                repositoryModule(),
                interactorModule(),
                utilModule()
            )
        }
        Coil.setImageLoader(
            ImageLoader.Builder(applicationContext)
                .respectCacheHeaders(false)
                .build()
        )
    }
}

object AndroidAppInfo : AppInfo {
    override val appId: String = "BuildConfig.APPLICATION_ID"
}

