package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.domain.worker.*
import com.bunbeauty.papakarlo.worker.cafe.CafeWorkerUtil
import com.bunbeauty.papakarlo.worker.city.CityWorkerUtil
import com.bunbeauty.papakarlo.worker.delivery.DeliveryWorkerUtil
import com.bunbeauty.papakarlo.worker.menu_product.MenuProductWorkerUtil
import com.bunbeauty.papakarlo.worker.street.StreetWorkerUtil
import com.bunbeauty.papakarlo.worker.user.UserWorkerUtil
import com.example.data_api.mapper.CafeMapper
import com.example.domain_api.mapper.ICafeMapper
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
}

fun workerModule() = module {
    single { CafeWorkerUtil() } bind ICafeWorkerUtil::class
    single { StreetWorkerUtil() } bind IStreetWorkerUtil::class
    single { CityWorkerUtil() } bind ICityWorkerUtil::class
    single { DeliveryWorkerUtil() } bind IDeliveryWorkerUtil::class
    single { MenuProductWorkerUtil() } bind IMenuProductWorkerUtil::class
    single { UserWorkerUtil() } bind IUserWorkerUtil::class
}
