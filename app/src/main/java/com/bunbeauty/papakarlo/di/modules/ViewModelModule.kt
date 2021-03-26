package com.bunbeauty.papakarlo.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bunbeauty.papakarlo.di.ViewModelKey
import com.bunbeauty.papakarlo.presentation.*
import com.bunbeauty.papakarlo.presentation.base.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun provideViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ProductsViewModel::class)
    internal abstract fun provideProductsViewModel(productsViewModel: ProductsViewModel): ViewModel

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
    @ViewModelKey(CreationOrderViewModel::class)
    internal abstract fun provideCreationOrderViewModel(creationOrderViewModel: CreationOrderViewModel): ViewModel

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
    @ViewModelKey(AddressesViewModel::class)
    internal abstract fun provideAddressesViewModel(addressesViewModel: AddressesViewModel): ViewModel

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
}