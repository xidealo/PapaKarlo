package com.bunbeauty.papakarlo.di.components

import androidx.lifecycle.ViewModelStoreOwner
import com.bunbeauty.papakarlo.di.modules.ViewModelModule
import com.bunbeauty.papakarlo.feature.address.cafe_address_list.CafeAddressListBottomSheet
import com.bunbeauty.papakarlo.feature.address.create_address.CreateAddressFragment
import com.bunbeauty.papakarlo.feature.address.user_address_list.UserAddressListBottomSheet
import com.bunbeauty.papakarlo.feature.auth.confirm.ConfirmFragment
import com.bunbeauty.papakarlo.feature.auth.login.LoginFragment
import com.bunbeauty.papakarlo.feature.cafe.cafe_list.CafeListFragment
import com.bunbeauty.papakarlo.feature.cafe.cafe_options.CafeOptionsBottomSheet
import com.bunbeauty.papakarlo.feature.consumer_cart.ConsumerCartFragment
import com.bunbeauty.papakarlo.feature.create_order.CreateOrderFragment
import com.bunbeauty.papakarlo.feature.create_order.deferred_time.DeferredTimeBottomSheet
import com.bunbeauty.papakarlo.feature.edit_text.EditTextBottomSheet
import com.bunbeauty.papakarlo.feature.main.MainActivity
import com.bunbeauty.papakarlo.feature.menu.MenuFragment
import com.bunbeauty.papakarlo.feature.product_details.ProductDetailsFragment
import com.bunbeauty.papakarlo.feature.profile.ProfileFragment
import com.bunbeauty.papakarlo.feature.profile.about_app.AboutAppBottomSheet
import com.bunbeauty.papakarlo.feature.profile.feedback.FeedbackBottomSheet
import com.bunbeauty.papakarlo.feature.profile.order.order_details.OrderDetailsFragment
import com.bunbeauty.papakarlo.feature.profile.order.order_list.OrderListFragment
import com.bunbeauty.papakarlo.feature.profile.payment.PaymentBottomSheet
import com.bunbeauty.papakarlo.feature.profile.settings.SettingsFragment
import com.bunbeauty.papakarlo.feature.profile.settings.change_city.ChangeCityBottomSheet
import com.bunbeauty.papakarlo.feature.profile.settings.logout.LogoutBottomSheet
import com.bunbeauty.papakarlo.feature.select_city.SelectCityFragment
import com.bunbeauty.papakarlo.feature.splash.SplashFragment
import com.bunbeauty.papakarlo.feature.update.UpdateFragment
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
    fun inject(productDetailsFragment: ProductDetailsFragment)
    fun inject(menuFragment: MenuFragment)
    fun inject(consumerCartFragment: ConsumerCartFragment)
    fun inject(createOrderFragment: CreateOrderFragment)
    fun inject(cafeListFragment: CafeListFragment)
    fun inject(orderListFragment: OrderListFragment)
    fun inject(createAddressFragment: CreateAddressFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(confirmFragment: ConfirmFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(selectCityFragment: SelectCityFragment)
    fun inject(splashFragment: SplashFragment)
    fun inject(updateFragment: UpdateFragment)

    //bottom sheet
    fun inject(userAddressListBottomSheet: UserAddressListBottomSheet)
    fun inject(cafeOptionsBottomSheet: CafeOptionsBottomSheet)
    fun inject(orderDetailsFragment: OrderDetailsFragment)
    fun inject(editTextBottomSheet: EditTextBottomSheet)
    fun inject(paymentBottomSheet: PaymentBottomSheet)
    fun inject(deferredTimeBottomSheet: DeferredTimeBottomSheet)
    fun inject(feedbackBottomSheet: FeedbackBottomSheet)
    fun inject(changeCityBottomSheet: ChangeCityBottomSheet)
    fun inject(cafeAddressListBottomSheet: CafeAddressListBottomSheet)
    fun inject(logoutBottomSheet: LogoutBottomSheet)
    fun inject(aboutAppBottomSheet: AboutAppBottomSheet)
}