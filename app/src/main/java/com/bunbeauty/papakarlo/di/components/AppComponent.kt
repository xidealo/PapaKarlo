package com.bunbeauty.papakarlo.di.components

import android.content.Context
import com.bunbeauty.data.di.DataModule
import com.bunbeauty.data.di.MapperModule
import com.bunbeauty.data.di.RepositoryModule
import com.bunbeauty.domain.di.InteractorModule
import com.bunbeauty.domain.di.UtilModule
import com.bunbeauty.papakarlo.PapaKarloApplication
import com.bunbeauty.papakarlo.common.custom_view.NavigationCard
import com.bunbeauty.papakarlo.common.custom_view.TextCard
import com.bunbeauty.papakarlo.di.modules.AppModule
import com.bunbeauty.papakarlo.di.modules.AppUtilModule
import com.bunbeauty.papakarlo.di.modules.UIMapperModule
import com.bunbeauty.papakarlo.di.modules.WorkerModule
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
        AppModule::class,
        AppUtilModule::class,
        UIMapperModule::class,
        WorkerModule::class,
        RepositoryModule::class,
        DataModule::class,
        InteractorModule::class,
        UtilModule::class,
        MapperModule::class,
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

    // View
    fun inject(textCard: TextCard)
    fun inject(navigationCard: NavigationCard)
}