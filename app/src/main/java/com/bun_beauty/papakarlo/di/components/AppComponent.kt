package com.bun_beauty.papakarlo.di.components

import android.content.Context
import com.bun_beauty.papakarlo.di.modules.ApiModule
import com.bun_beauty.papakarlo.di.modules.AppModule
import com.bun_beauty.papakarlo.di.modules.DataModule
import com.bun_beauty.papakarlo.di.modules.RepositoryModule
import com.bun_beauty.papakarlo.PapaKarloApplication
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