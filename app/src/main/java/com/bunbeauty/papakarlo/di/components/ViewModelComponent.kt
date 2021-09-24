package com.bunbeauty.papakarlo.di.components

import androidx.lifecycle.ViewModelStoreOwner
import com.bunbeauty.papakarlo.di.modules.ViewModelModule
import com.bunbeauty.papakarlo.ui.*
import com.bunbeauty.papakarlo.ui.fragment.SelectCityFragment
import com.bunbeauty.papakarlo.ui.fragment.address.CafeAddressesBottomSheet
import com.bunbeauty.papakarlo.ui.fragment.address.CreateAddressFragment
import com.bunbeauty.papakarlo.ui.fragment.address.UserAddressesBottomSheet
import com.bunbeauty.papakarlo.ui.fragment.create_order.CreateOrderFragment
import com.bunbeauty.papakarlo.ui.fragment.create_order.DeferredTimeBottomSheet
import com.bunbeauty.papakarlo.ui.fragment.profile.FeedbackBottomSheet
import com.bunbeauty.papakarlo.ui.fragment.profile.PaymentBottomSheet
import com.bunbeauty.papakarlo.ui.fragment.profile.ProfileFragment
import com.bunbeauty.papakarlo.ui.fragment.profile.order.OrderDetailsFragment
import com.bunbeauty.papakarlo.ui.fragment.profile.order.OrdersFragment
import com.bunbeauty.papakarlo.ui.fragment.profile.settings.CitySelectionBottomSheet
import com.bunbeauty.papakarlo.ui.fragment.profile.settings.SettingsFragment
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
    fun inject(selectCityFragment: SelectCityFragment)

    //bottom sheet
    fun inject(userAddressesBottomSheet: UserAddressesBottomSheet)
    fun inject(cafeOptionsBottomSheet: CafeOptionsBottomSheet)
    fun inject(orderDetailsFragment: OrderDetailsFragment)
    fun inject(oneLineActionBottomSheet: OneLineActionBottomSheet)
    fun inject(paymentBottomSheet: PaymentBottomSheet)
    fun inject(deferredTimeBottomSheet: DeferredTimeBottomSheet)
    fun inject(feedbackBottomSheet: FeedbackBottomSheet)
    fun inject(citySelectionBottomSheet: CitySelectionBottomSheet)
    fun inject(cafeAddressesBottomSheet: CafeAddressesBottomSheet)
}