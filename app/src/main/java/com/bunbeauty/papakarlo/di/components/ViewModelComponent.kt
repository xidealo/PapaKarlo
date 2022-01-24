package com.bunbeauty.papakarlo.di.components

import androidx.lifecycle.ViewModelStoreOwner
import com.bunbeauty.papakarlo.di.modules.ViewModelModule
import com.bunbeauty.papakarlo.ui.MainActivity
import com.bunbeauty.papakarlo.ui.fragment.OneLineActionBottomSheet
import com.bunbeauty.papakarlo.ui.fragment.SelectCityFragment
import com.bunbeauty.papakarlo.ui.fragment.SplashFragment
import com.bunbeauty.papakarlo.ui.fragment.UpdateFragment
import com.bunbeauty.papakarlo.ui.fragment.address.CafeAddressesBottomSheet
import com.bunbeauty.papakarlo.ui.fragment.address.CreateAddressFragment
import com.bunbeauty.papakarlo.ui.fragment.address.UserAddressesBottomSheet
import com.bunbeauty.papakarlo.ui.fragment.auth.ConfirmFragment
import com.bunbeauty.papakarlo.ui.fragment.auth.LoginFragment
import com.bunbeauty.papakarlo.ui.fragment.cafe.CafeListFragment
import com.bunbeauty.papakarlo.ui.fragment.cafe.CafeOptionsBottomSheet
import com.bunbeauty.papakarlo.ui.fragment.cart.ConsumerCartFragment
import com.bunbeauty.papakarlo.ui.fragment.create_order.CreateOrderFragment
import com.bunbeauty.papakarlo.ui.fragment.create_order.DeferredTimeBottomSheet
import com.bunbeauty.papakarlo.ui.fragment.menu.MenuFragment
import com.bunbeauty.papakarlo.ui.fragment.menu.ProductFragment
import com.bunbeauty.papakarlo.ui.fragment.profile.AboutAppBottomSheet
import com.bunbeauty.papakarlo.ui.fragment.profile.FeedbackBottomSheet
import com.bunbeauty.papakarlo.ui.fragment.profile.PaymentBottomSheet
import com.bunbeauty.papakarlo.ui.fragment.profile.ProfileFragment
import com.bunbeauty.papakarlo.ui.fragment.profile.order.OrderDetailsFragment
import com.bunbeauty.papakarlo.ui.fragment.profile.order.OrdersFragment
import com.bunbeauty.papakarlo.ui.fragment.profile.settings.CitySelectionBottomSheet
import com.bunbeauty.papakarlo.ui.fragment.profile.settings.LogoutBottomSheet
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
    fun inject(splashFragment: SplashFragment)
    fun inject(updateFragment: UpdateFragment)

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
    fun inject(logoutBottomSheet: LogoutBottomSheet)
    fun inject(aboutAppBottomSheet: AboutAppBottomSheet)
}