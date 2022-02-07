package com.bunbeauty.shared.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        //common modules *from other modules like DATA
        /*networkModule(),
        repositoryModule(),
        mapperModule()*/
    )
}

fun initKoin() = initKoin(){

}

