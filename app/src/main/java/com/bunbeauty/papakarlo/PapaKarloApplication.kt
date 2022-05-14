package com.bunbeauty.papakarlo

import android.app.Application
import androidx.viewbinding.BuildConfig
import coil.Coil
import coil.ImageLoader
import com.bunbeauty.shared.data.di.databaseModule
import com.bunbeauty.shared.data.di.mapperModule
import com.bunbeauty.shared.data.di.networkModule
import com.bunbeauty.shared.data.di.repositoryModule
import com.bunbeauty.shared.di.interactorModule
import com.bunbeauty.shared.di.utilModule
import com.bunbeauty.papakarlo.di.appModule
import com.bunbeauty.papakarlo.di.appUtilModule
import com.bunbeauty.papakarlo.di.uiMapperModule
import com.bunbeauty.papakarlo.di.viewModelModule
import com.bunbeauty.shared.di.initKoin
import com.bunbeauty.shared.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.logger.Level

class PapaKarloApplication : Application(), KoinComponent {

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
                utilModule(),
                platformModule()
            )
        }
        Coil.setImageLoader(
            ImageLoader.Builder(applicationContext)
                .respectCacheHeaders(false)
                .build()
        )
    }
}

