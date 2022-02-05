package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.domain.worker.*
import com.bunbeauty.papakarlo.worker.cafe.CafeWorkerUtil
import com.bunbeauty.papakarlo.worker.category.CategoryWorkerUtil
import com.bunbeauty.papakarlo.worker.city.CityWorkerUtil
import com.bunbeauty.papakarlo.worker.delivery.DeliveryWorkerUtil
import com.bunbeauty.papakarlo.worker.menu_product.MenuProductWorkerUtil
import com.bunbeauty.papakarlo.worker.street.StreetWorkerUtil
import com.bunbeauty.papakarlo.worker.user.UserWorkerUtil
import dagger.Binds
import dagger.Module
import org.koin.dsl.bind
import org.koin.dsl.module

@Module
interface WorkerModule {

    @Binds
    fun bindsCafeWorkerUtil(cafeWorkerUtil: CafeWorkerUtil): ICafeWorkerUtil

    @Binds
    fun bindsStreetWorkerUtil(streetWorkerUtil: StreetWorkerUtil): IStreetWorkerUtil

    @Binds
    fun bindsCityWorkerUtil(cityWorkerUtil: CityWorkerUtil): ICityWorkerUtil

    @Binds
    fun bindsDeliveryWorkerUtil(deliveryWorkerUtil: DeliveryWorkerUtil): IDeliveryWorkerUtil

    @Binds
    fun bindsMenuProductWorkerUtil(menuProductWorkerUtil: MenuProductWorkerUtil): IMenuProductWorkerUtil

    @Binds
    fun bindsUserWorkerUtil(userWorkerUtil: UserWorkerUtil): IUserWorkerUtil

    @Binds
    fun bindsCategoryWorkerUtil(categoryWorkerUtil: CategoryWorkerUtil): ICategoryWorkerUtil
}

fun workerModule() = module {
    single<ICafeWorkerUtil> { CafeWorkerUtil() }
    single<IStreetWorkerUtil> { StreetWorkerUtil() }
    single<ICityWorkerUtil> { CityWorkerUtil() }
    single<IDeliveryWorkerUtil> { DeliveryWorkerUtil() }
    single<IMenuProductWorkerUtil> { MenuProductWorkerUtil() }
    single<IUserWorkerUtil> { UserWorkerUtil() }
    single<ICategoryWorkerUtil> { CategoryWorkerUtil() }
}
