package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.domain.interactor.cafe.CafeInteractor
import com.bunbeauty.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.domain.interactor.cart.CartProductInteractor
import com.bunbeauty.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.domain.interactor.city.CityInteractor
import com.bunbeauty.domain.interactor.city.ICityInteractor
import com.bunbeauty.domain.interactor.main.IMainInteractor
import com.bunbeauty.domain.interactor.main.MainInteractor
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.domain.interactor.user.UserInteractor
import dagger.Binds
import dagger.Module
import org.koin.dsl.bind
import org.koin.dsl.module

@Module
interface InteractorModule {

    @Binds
    fun bindsMainInteractor(mainInteractor: MainInteractor): IMainInteractor

    @Binds
    fun bindsUserInteractor(userInteractor: UserInteractor): IUserInteractor

    @Binds
    fun bindsCityInteractor(cityInteractor: CityInteractor): ICityInteractor

    @Binds
    fun bindsCartProductInteractor(cartProductInteractor: CartProductInteractor): ICartProductInteractor

    @Binds
    fun bindsCafeInteractor(cafeInteractor: CafeInteractor): ICafeInteractor
}

fun interactorModule() = module {
    single {
        MainInteractor(
            cityWorkerUtil = get(),
            menuProductWorkerUtil = get(),
            deliveryWorkerUtil = get(),
            userWorkerUtil = get(),
            authUtil = get(),
            )
    } bind IMainInteractor::class
    single {
        UserInteractor(
            userRepo = get(),
            userWorkerUtil = get(),
            authUtil = get(),
            )
    } bind IUserInteractor::class
    single {
        CityInteractor(

            dataStoreRepo = get(),
            cityRepo = get(),
            cafeWorkerUtil = get(),
            streetWorkerUtil = get(),
        )
    } bind ICityInteractor::class
    single {
        CartProductInteractor(
            cartProductRepo = get(),
            )
    } bind ICartProductInteractor::class
    single {
        CafeInteractor(
            cafeRepo = get(),
            dateTimeUtil = get(),
        )
    } bind ICafeInteractor::class

}
