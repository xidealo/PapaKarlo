package com.bunbeauty.papakarlo.di.components

import android.content.Context
import com.bunbeauty.data.di.DataModule
import com.bunbeauty.papakarlo.PapaKarloApplication
import com.bunbeauty.papakarlo.di.modules.*
import com.bunbeauty.papakarlo.worker.cafe.RefreshCafeWorker
import com.bunbeauty.papakarlo.worker.category.RefreshCategoryWorker
import com.bunbeauty.papakarlo.worker.city.RefreshCityWorker
import com.bunbeauty.papakarlo.worker.delivery.RefreshDeliveryWorker
import com.bunbeauty.papakarlo.worker.menu_product.RefreshMenuProductWorker
import com.bunbeauty.papakarlo.worker.street.RefreshStreetWorker
import com.bunbeauty.papakarlo.worker.user.RefreshUserWorker
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
        UIMapperModule::class,
        AppUtilModule::class,
        DataModule::class,
        WorkerModule::class,
        InteractorModule::class,
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun getViewModelComponent(): ViewModelComponent.Factory

    fun inject(application: PapaKarloApplication)

    // Worker

    fun inject(refreshCafeWorker: RefreshCafeWorker)
    fun inject(refreshStreetWorker: RefreshStreetWorker)
    fun inject(refreshCityWorker: RefreshCityWorker)
    fun inject(refreshDeliveryWorker: RefreshDeliveryWorker)
    fun inject(refreshMenuProductWorker: RefreshMenuProductWorker)
    fun inject(refreshUserWorker: RefreshUserWorker)
    fun inject(refreshCategoryWorker: RefreshCategoryWorker)
}