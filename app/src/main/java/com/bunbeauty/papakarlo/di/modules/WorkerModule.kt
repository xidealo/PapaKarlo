package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.domain.worker.*
import com.bunbeauty.papakarlo.worker.cafe.CafeWorkerUtil
import com.bunbeauty.papakarlo.worker.city.CityWorkerUtil
import com.bunbeauty.papakarlo.worker.delivery.DeliveryWorkerUtil
import com.bunbeauty.papakarlo.worker.street.StreetWorkerUtil
import com.bunbeauty.papakarlo.worker.user.UserWorkerUtil
import org.koin.dsl.module

fun workerModule() = module {
    single<ICafeWorkerUtil> { CafeWorkerUtil(workManager = get()) }
    single<IStreetWorkerUtil> { StreetWorkerUtil(workManager = get()) }
    single<ICityWorkerUtil> { CityWorkerUtil(workManager = get()) }
    single<IDeliveryWorkerUtil> { DeliveryWorkerUtil(workManager = get()) }
    single<IUserWorkerUtil> { UserWorkerUtil(workManager = get()) }
}
