package com.bunbeauty.papakarlo

import android.app.Application
import com.bunbeauty.papakarlo.di.components.AppComponent
import com.bunbeauty.papakarlo.di.components.DaggerAppComponent
import com.instacart.library.truetime.TrueTime
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PapaKarloApplication : Application(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job()

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()

        appComponent.inject(this)
        launch(Dispatchers.IO) {
            try {
                TrueTime.build().initialize()
            }catch (exception:Exception){
                //try reconnect
            }
        }
    }
}