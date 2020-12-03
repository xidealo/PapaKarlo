package com.bunbeauty.papakarlo.di.components

import androidx.lifecycle.ViewModelStoreOwner
import com.bunbeauty.papakarlo.ui.ProductFragment
import com.bunbeauty.papakarlo.di.modules.ViewModelModule
import com.bunbeauty.papakarlo.ui.ConsumerCartFragment
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.ui.ProductsFragment
import com.bunbeauty.papakarlo.ui.view.MenuFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
interface ViewModelComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance viewModelStoreOwner: ViewModelStoreOwner): ViewModelComponent
    }

    // activities
    fun inject(mainActivity: MainActivity)

    // fragments
    fun inject(productsFragment: ProductsFragment)
    fun inject(productFragment: ProductFragment)
    fun inject(menuFragment: MenuFragment)
    fun inject(consumerCartFragment: ConsumerCartFragment)

    // dialogs
}