package com.bunbeauty.papakarlo.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bunbeauty.papakarlo.di.ViewModelKey
import com.bunbeauty.papakarlo.presentation.*
import com.bunbeauty.papakarlo.presentation.base.ViewModelFactory
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
    @ViewModelKey(ProductTabViewModelImpl::class)
    internal abstract fun provideProductTabViewModel(productTabViewModelImpl: ProductTabViewModelImpl): ViewModel

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
    @ViewModelKey(CreationOrderViewModelImpl::class)
    internal abstract fun provideCreationOrderViewModelImpl(creationOrderViewModelImpl: CreationOrderViewModelImpl): ViewModel

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
    @ViewModelKey(OrderViewModel::class)
    internal abstract fun provideOrderViewModel(orderViewModel: OrderViewModel): ViewModel

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
    @ViewModelKey(SettingsViewModelImpl::class)
    internal abstract fun provideSettingsViewModel(settingsViewModelImpl: SettingsViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LogoutViewModel::class)
    internal abstract fun provideLogoutViewModel(logoutViewModel: LogoutViewModel): ViewModel
}