package com.bunbeauty.papakarlo.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bunbeauty.papakarlo.di.ViewModelKey
import com.bunbeauty.papakarlo.view_model.*
import com.bunbeauty.papakarlo.view_model.base.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun provideViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    internal abstract fun provideProductViewModel(productViewModel: ProductViewModel): ViewModel

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
    @ViewModelKey(MenuViewModel::class)
    internal abstract fun provideMenuViewModel(menuViewModel: MenuViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ConsumerCartViewModel::class)
    internal abstract fun provideConsumerCartViewModel(consumerCartViewModel: ConsumerCartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderViewModel::class)
    internal abstract fun provideOrderViewModel(orderViewModel: OrderViewModel): ViewModel
}