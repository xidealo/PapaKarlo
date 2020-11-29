package com.bun_beauty.papakarlo.di.components

import androidx.lifecycle.ViewModelStoreOwner
import com.bun_beauty.papakarlo.ProductActivity
import com.bun_beauty.papakarlo.di.modules.ViewModelModule
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
    // main fragments

    // fragments

    // dialogs


}