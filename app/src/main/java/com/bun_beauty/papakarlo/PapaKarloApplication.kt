package com.bun_beauty.papakarlo

import android.app.Application
import com.bun_beauty.papakarlo.di.components.AppComponent
import com.bun_beauty.papakarlo.di.components.DaggerAppComponent

class PapaKarloApplication: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }
}