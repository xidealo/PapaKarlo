package com.bunbeauty.papakarlo.di.components

import androidx.lifecycle.ViewModelStoreOwner
import com.bunbeauty.papakarlo.di.modules.ViewModelModule
import com.bunbeauty.papakarlo.ui.*
import com.bunbeauty.papakarlo.ui.fragment.create_order.CreateOrderFragment
import com.bunbeauty.papakarlo.ui.fragment.create_order.DeferredTimeBottomSheet
import com.bunbeauty.papakarlo.ui.profile.*
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
    fun inject(createOrderFragment: CreateOrderFragment)
    fun inject(cafeListFragment: CafeListFragment)
    fun inject(ordersFragment: OrdersFragment)
    fun inject(createAddressFragment: CreateAddressFragment)
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
    fun inject(deferredTimeBottomSheet: DeferredTimeBottomSheet)
    fun inject(feedbackBottomSheet: FeedbackBottomSheet)
}