package com.bunbeauty.papakarlo

import android.app.Application
import com.bunbeauty.papakarlo.di.components.AppComponent
import com.bunbeauty.papakarlo.di.components.DaggerAppComponent

class PapaKarloApplication: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }
}