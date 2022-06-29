package com.bunbeauty.shared.di

import com.bunbeauty.shared.data.di.databaseModule
import com.bunbeauty.shared.data.di.mapperModule
import com.bunbeauty.shared.data.di.networkModule
import com.bunbeauty.shared.data.di.repositoryModule
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.domain.interactor.city.ICityInteractor
import com.bunbeauty.shared.domain.interactor.main.MainInteractor
import com.bunbeauty.shared.domain.interactor.menu_product.IMenuProductInteractor
import com.bunbeauty.shared.domain.interactor.menu_product.MenuProductInteractor
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        databaseModule(),
        networkModule(),
        mapperModule(),
        repositoryModule(),
        interactorModule(),
        utilModule(),
        platformModule()
    )
}

fun initKoin() = startKoin {
    modules(
        databaseModule(),
        networkModule(),
        mapperModule(),
        repositoryModule(),
        interactorModule(),
        utilModule(),
        platformModule()
    )
}

class IosComponent:KoinComponent {
    fun provideMainInteractor(): MainInteractor = get()
    fun provideCityInteractor(): ICityInteractor = get()
    fun provideApiRepo(): NetworkConnector = get()
    fun provideMenuInteractor(): IMenuProductInteractor = get()
}
