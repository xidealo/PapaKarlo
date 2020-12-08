package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.papakarlo.data.local.db.order.OrderRepo
import com.bunbeauty.papakarlo.data.local.db.order.OrderRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindOrderRepo(orderRepository: OrderRepository): OrderRepo
}