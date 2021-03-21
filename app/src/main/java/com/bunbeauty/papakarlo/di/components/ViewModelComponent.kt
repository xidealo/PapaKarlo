package com.bunbeauty.papakarlo.di.components

import androidx.lifecycle.ViewModelStoreOwner
import com.bunbeauty.papakarlo.di.modules.ViewModelModule
import com.bunbeauty.papakarlo.ui.addresses.AddressesBottomSheet
import com.bunbeauty.papakarlo.ui.consumer_cart.ConsumerCartFragment
import com.bunbeauty.papakarlo.ui.cafe_list.CafeListFragment
import com.bunbeauty.papakarlo.ui.cafe_options.CafeOptionsBottomSheet
import com.bunbeauty.papakarlo.ui.creation_order.CreationOrderFragment
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.ui.menu.MenuFragment
import com.bunbeauty.papakarlo.ui.creation_address.CreationAddressFragment
import com.bunbeauty.papakarlo.ui.orders.OrdersFragment
import com.bunbeauty.papakarlo.ui.product.ProductFragment
import com.bunbeauty.papakarlo.ui.products.ProductsFragment
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