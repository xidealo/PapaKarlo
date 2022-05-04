package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.domain.worker.*
import com.bunbeauty.papakarlo.worker.street.StreetWorkerUtil
import org.koin.dsl.module

fun workerModule() = module {
    single<IStreetWorkerUtil> { StreetWorkerUtil(workManager = get()) }
}