package com.bunbeauty.papakarlo.di.components

import androidx.lifecycle.ViewModelStoreOwner
import com.bunbeauty.papakarlo.ui.ProductActivity
import com.bunbeauty.papakarlo.di.modules.ViewModelModule
import com.bunbeauty.papakarlo.ui.ConsumerCartActivity
import com.bunbeauty.papakarlo.ui.ProductMenuActivity
import com.bunbeauty.papakarlo.ui.ProductsFragment
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

    // fragments
    fun inject(productsFragment: ProductsFragment)

    // dialogs
}