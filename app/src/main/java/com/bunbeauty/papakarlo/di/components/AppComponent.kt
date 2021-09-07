package com.bunbeauty.papakarlo.di.components

import android.content.Context
import com.bunbeauty.papakarlo.PapaKarloApplication
import com.bunbeauty.papakarlo.di.modules.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiDataModule::class,
        ApiMapperModule::class,
        ApiRepositoryModule::class,
        AppModule::class,
        FirebaseDataModule::class,
        FirebaseMapperModule::class,
        FirebaseRepositoryModule::class,
        UtilModule::class,
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