package com.example.papakarlo.di.components

import com.example.papakarlo.di.modules.ActivityModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ActivityModule::class])
interface ActivityComponent {

}