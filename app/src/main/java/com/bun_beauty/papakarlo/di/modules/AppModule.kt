package com.bun_beauty.papakarlo.di.modules

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.bun_beauty.papakarlo.di.components.ViewModelComponent
import dagger.Module
import dagger.Provides

@Module(subcomponents = [ViewModelComponent::class])
class AppModule {

    @Provides
    fun provideLinearLayoutManager(context: Context) = LinearLayoutManager(context)
}