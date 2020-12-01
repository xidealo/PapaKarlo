package com.bunbeauty.papakarlo.di.components

import android.content.Context
import com.bunbeauty.papakarlo.di.modules.ApiModule
import com.bunbeauty.papakarlo.di.modules.AppModule
import com.bunbeauty.papakarlo.di.modules.DataModule
import com.bunbeauty.papakarlo.di.modules.RepositoryModule
import com.bunbeauty.papakarlo.PapaKarloApplication
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        DataModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun getViewModelComponent(): ViewModelComponent.Factory

    fun inject(application: PapaKarloApplication)
}