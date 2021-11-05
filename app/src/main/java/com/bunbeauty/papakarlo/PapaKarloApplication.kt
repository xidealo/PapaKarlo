package com.bunbeauty.papakarlo

import android.app.Application
import com.bunbeauty.papakarlo.di.components.AppComponent
import com.bunbeauty.papakarlo.di.components.DaggerAppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class PapaKarloApplication : Application(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job()

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        setTheme(R.style.AppTheme)
        super.onCreate()

        appComponent.inject(this)
    }
}
