package com.bunbeauty.papakarlo.di.components

import androidx.lifecycle.ViewModelStoreOwner
import com.bunbeauty.papakarlo.di.modules.ViewModelModule
import com.bunbeauty.papakarlo.ui.*
import com.bunbeauty.papakarlo.ui.cafe_list.CafeListFragment
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
    fun inject(productTabFragment: ProductTabFragment)
    fun inject(productFragment: ProductFragment)
    fun inject(menuFragment: MenuFragment)
    fun inject(consumerCartFragment: ConsumerCartFragment)
    fun inject(creationOrderFragment: CreationOrderFragment)
    fun inject(cafeListFragment: CafeListFragment)
    fun inject(ordersFragment: OrdersFragment)
    fun inject(creationAddressFragment: CreationAddressFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(confirmFragment: ConfirmFragment)
    fun inject(loginFragment: LoginFragment)

    //bottom sheet
    fun inject(addressesBottomSheet: AddressesBottomSheet)
    fun inject(cafeOptionsBottomSheet: CafeOptionsBottomSheet)
    fun inject(orderBottomSheet: OrderBottomSheet)

    // dialogs
}