package com.bunbeauty.papakarlo.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bunbeauty.papakarlo.di.ViewModelKey
import com.bunbeauty.papakarlo.presentation.EmptyViewModel
import com.bunbeauty.papakarlo.presentation.MainViewModel
import com.bunbeauty.papakarlo.presentation.OneLineActionViewModel
import com.bunbeauty.papakarlo.presentation.address.AddressesViewModelImpl
import com.bunbeauty.papakarlo.presentation.address.CreationAddressViewModel
import com.bunbeauty.papakarlo.presentation.base.ViewModelFactory
import com.bunbeauty.papakarlo.presentation.cafe.CafeListViewModel
import com.bunbeauty.papakarlo.presentation.cafe.CafeOptionsViewModel
import com.bunbeauty.papakarlo.presentation.cart.ConsumerCartViewModel
import com.bunbeauty.papakarlo.presentation.create_order.CreateOrderViewModel
import com.bunbeauty.papakarlo.presentation.create_order.DeferredTimeViewModel
import com.bunbeauty.papakarlo.presentation.login.ConfirmViewModelImpl
import com.bunbeauty.papakarlo.presentation.login.LoginViewModel
import com.bunbeauty.papakarlo.presentation.menu.MenuViewModel
import com.bunbeauty.papakarlo.presentation.menu.ProductTabViewModel
import com.bunbeauty.papakarlo.presentation.menu.ProductViewModel
import com.bunbeauty.papakarlo.presentation.profile.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun provideViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ProductTabViewModel::class)
    internal abstract fun provideProductTabViewModel(productTabViewModel: ProductTabViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MenuViewModel::class)
    internal abstract fun provideMenuViewModel(menuViewModel: MenuViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun provideMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ConsumerCartViewModel::class)
    internal abstract fun provideConsumerCartViewModel(consumerCartViewModel: ConsumerCartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateOrderViewModel::class)
    internal abstract fun provideCreationOrderViewModelImpl(creationOrderViewModel: CreateOrderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CafeListViewModel::class)
    internal abstract fun provideContactsViewModel(cafeListViewModel: CafeListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrdersViewModel::class)
    internal abstract fun provideOrdersViewModel(ordersViewModel: OrdersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreationAddressViewModel::class)
    internal abstract fun provideCreationAddressViewModel(creationAddressViewModel: CreationAddressViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddressesViewModelImpl::class)
    internal abstract fun provideAddressesViewModelImpl(addressesViewModelImpl: AddressesViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CafeOptionsViewModel::class)
    internal abstract fun provideCafeOptionsViewModel(cafeOptionsViewModel: CafeOptionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EmptyViewModel::class)
    internal abstract fun provideEmptyViewModel(emptyViewModel: EmptyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderDetailsViewModel::class)
    internal abstract fun provideOrderViewModel(orderDetailsViewModel: OrderDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    internal abstract fun provideProfileViewModel(profileViewModelImpl: ProfileViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun provideLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ConfirmViewModelImpl::class)
    internal abstract fun provideConfirmViewModel(confirmViewModelImpl: ConfirmViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    internal abstract fun provideSettingsViewModel(settingsViewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    internal abstract fun provideProductViewModel(productViewModel: ProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OneLineActionViewModel::class)
    internal abstract fun provideOneLineActionViewModel(oneLineActionViewModel: OneLineActionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DeferredTimeViewModel::class)
    internal abstract fun provideDeferredTimeViewModel(deferredTimeViewModel: DeferredTimeViewModel): ViewModel
}