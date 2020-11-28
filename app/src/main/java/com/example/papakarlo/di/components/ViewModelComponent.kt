package com.example.papakarlo.di.components

import androidx.lifecycle.ViewModelStoreOwner
import com.example.papakarlo.ProductActivity
import com.example.papakarlo.di.modules.ViewModelModule
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