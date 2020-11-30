package com.bun_beauty.papakarlo.di.components

import androidx.lifecycle.ViewModelStoreOwner
import com.bun_beauty.papakarlo.ui.activity.ProductActivity
import com.bun_beauty.papakarlo.di.modules.ViewModelModule
import com.bun_beauty.papakarlo.ui.activity.ConsumerCartActivity
import com.bun_beauty.papakarlo.ui.activity.ProductMenuActivity
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
interface ViewModelComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance viewModelStoreOwner: ViewModelStoreOwner): ViewModelComponent
    }

    // activities
    fun inject(productActivity: ProductActivity)
    fun inject(productMenuActivity: ProductMenuActivity)
    fun inject(consumerCartActivity: ConsumerCartActivity)
    // main fragments

    // fragments

    // dialogs
}