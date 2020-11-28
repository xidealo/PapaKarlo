package com.example.papakarlo.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.papakarlo.di.ViewModelKey
import com.example.papakarlo.view_model.ProductViewModel
import com.example.papakarlo.view_model.base.ViewModelFactory
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
    internal abstract fun provideLogsMainViewModel(productViewModel: ProductViewModel): ViewModel

}