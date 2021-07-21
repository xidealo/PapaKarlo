package com.bunbeauty.papakarlo.di.components

import androidx.lifecycle.ViewModelStoreOwner
import com.bunbeauty.papakarlo.di.modules.ViewModelModule
import com.bunbeauty.papakarlo.ui.*
import com.bunbeauty.papakarlo.ui.CafeListFragment
import com.bunbeauty.papakarlo.ui.profile.OrderDetailsFragment
import com.bunbeauty.papakarlo.ui.profile.OrdersFragment
import com.bunbeauty.papakarlo.ui.profile.ProfileFragment
import com.bunbeauty.papakarlo.ui.profile.SettingsFragment
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
    fun inject(settingsFragment: SettingsFragment)

    //bottom sheet
    fun inject(addressesBottomSheet: AddressesBottomSheet)
    fun inject(cafeOptionsBottomSheet: CafeOptionsBottomSheet)
    fun inject(orderDetailsFragment: OrderDetailsFragment)
    fun inject(oneLineActionBottomSheet: OneLineActionBottomSheet)
    fun inject(paymentBottomSheet: PaymentBottomSheet)

    // dialogs
}