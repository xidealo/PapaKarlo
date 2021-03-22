package com.bunbeauty.papakarlo.di.components

import androidx.lifecycle.ViewModelStoreOwner
import com.bunbeauty.papakarlo.di.modules.ViewModelModule
import com.bunbeauty.papakarlo.ui.AddressesBottomSheet
import com.bunbeauty.papakarlo.ui.ConsumerCartFragment
import com.bunbeauty.papakarlo.ui.cafe_list.CafeListFragment
import com.bunbeauty.papakarlo.ui.CafeOptionsBottomSheet
import com.bunbeauty.papakarlo.ui.CreationOrderFragment
import com.bunbeauty.papakarlo.ui.MainActivity
import com.bunbeauty.papakarlo.ui.MenuFragment
import com.bunbeauty.papakarlo.ui.CreationAddressFragment
import com.bunbeauty.papakarlo.ui.OrdersFragment
import com.bunbeauty.papakarlo.ui.ProductFragment
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
    fun inject(mainActivity: MainActivity)

    // fragments
    fun inject(productsFragment: ProductsFragment)
    fun inject(productFragment: ProductFragment)
    fun inject(menuFragment: MenuFragment)
    fun inject(consumerCartFragment: ConsumerCartFragment)
    fun inject(creationOrderFragment: CreationOrderFragment)
    fun inject(cafeListFragment: CafeListFragment)
    fun inject(ordersFragment: OrdersFragment)
    fun inject(creationAddressFragment: CreationAddressFragment)

    //bottom sheet
    fun inject(addressesBottomSheet: AddressesBottomSheet)
    fun inject(cafeOptionsBottomSheet: CafeOptionsBottomSheet)

    // dialogs
}