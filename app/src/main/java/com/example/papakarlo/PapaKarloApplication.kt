package com.example.papakarlo

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.bunbeauty.eld_android.data.local.storage.DataStoreHelper
import com.bunbeauty.eld_android.di.component.AppComponent
import com.bunbeauty.eld_android.di.component.DaggerAppComponent
import com.example.papakarlo.di.components.AppComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class PapaKarloApplication: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }
}