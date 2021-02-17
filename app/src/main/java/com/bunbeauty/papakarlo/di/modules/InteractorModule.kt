package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.domain.interactor.address.AddressInteractor
import com.bunbeauty.domain.interactor.address.IAddressInteractor
import com.bunbeauty.domain.interactor.cafe.CafeInteractor
import com.bunbeauty.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.domain.interactor.cart.CartProductInteractor
import com.bunbeauty.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.domain.interactor.city.CityInteractor
import com.bunbeauty.domain.interactor.city.ICityInteractor
import com.bunbeauty.domain.interactor.main.IMainInteractor
import com.bunbeauty.domain.interactor.main.MainInteractor
import com.bunbeauty.domain.interactor.order.IOrderInteractor
import com.bunbeauty.domain.interactor.order.OrderInteractor
import com.bunbeauty.domain.interactor.street.IStreetInteractor
import com.bunbeauty.domain.interactor.street.StreetInteractor
import com.bunbeauty.domain.interactor.update.IUpdateInteractor
import com.bunbeauty.domain.interactor.update.UpdateInteractor
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

    @Binds
    fun bindsUpdateInteractor(updateInteractor: UpdateInteractor): IUpdateInteractor

    @Binds
    fun bindsOrderInteractor(orderInteractor: OrderInteractor): IOrderInteractor

    @Binds
    fun bindsStreetInteractor(streetInteractor: StreetInteractor): IStreetInteractor

    @Binds
    fun bindsAddressInteractor(addressInteractor: AddressInteractor): IAddressInteractor
}

fun interactorModule() = module {
    single {
        MainInteractor(
            cityWorkerUtil = get(),
            menuProductWorkerUtil = get(),
            deliveryWorkerUtil = get(),
            userWorkerUtil = get(),
            dataStoreRepo = get(),
        )
    } bind IMainInteractor::class
    single {
        UserInteractor(
            userRepo = get(),
            userWorkerUtil = get(),
            dataStoreRepo = get(),
            authRepo = get(),
            orderInteractor = get(),
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
            authRepo = get(),
            dataStoreRepo = get(),
        )
    } bind ICafeInteractor::class
    single<IUpdateInteractor> { UpdateInteractor(versionRepo = get()) }
    single<IOrderInteractor> { OrderInteractor() }
    single<IAddressInteractor> {
        AddressInteractor(
            dataStoreRepo = get(),
            streetRepo = get(),
            userAddressRepo = get(),
            userInteractor = get(),
        )
    }
    single<IStreetInteractor> {
        StreetInteractor(
            streetRepo = get(),
            dataStoreRepo = get(),
        )
    }
}
