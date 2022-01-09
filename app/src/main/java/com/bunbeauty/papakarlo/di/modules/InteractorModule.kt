package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.domain.interactor.address.AddressInteractor
import com.bunbeauty.domain.interactor.address.IAddressInteractor
import com.bunbeauty.domain.interactor.cafe.CafeInteractor
import com.bunbeauty.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.domain.interactor.cart.CartProductInteractor
import com.bunbeauty.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.domain.interactor.categories.CategoryInteractor
import com.bunbeauty.domain.interactor.categories.ICategoryInteractor
import com.bunbeauty.domain.interactor.city.CityInteractor
import com.bunbeauty.domain.interactor.city.ICityInteractor
import com.bunbeauty.domain.interactor.deferred_time.DeferredTimeInteractor
import com.bunbeauty.domain.interactor.deferred_time.IDeferredTimeInteractor
import com.bunbeauty.domain.interactor.main.IMainInteractor
import com.bunbeauty.domain.interactor.main.MainInteractor
import com.bunbeauty.domain.interactor.menu_product.IMenuProductInteractor
import com.bunbeauty.domain.interactor.menu_product.MenuProductInteractor
import com.bunbeauty.domain.interactor.order.IOrderInteractor
import com.bunbeauty.domain.interactor.order.OrderInteractor
import com.bunbeauty.domain.interactor.product.IProductInteractor
import com.bunbeauty.domain.interactor.product.ProductInteractor
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

    @Binds
    fun bindsCategoryInteractor(categoryInteractor: CategoryInteractor): ICategoryInteractor

    @Binds
    fun bindsMenuProductInteractor(menuProductInteractor: MenuProductInteractor): IMenuProductInteractor

    @Binds
    fun bindsDeferredTimeInteractor(deferredTimeInteractor: DeferredTimeInteractor): IDeferredTimeInteractor

    @Binds
    fun bindsProductInteractor(productInteractor: ProductInteractor): IProductInteractor
}

fun interactorModule() = module {
    single {
        MainInteractor(
            cityWorkerUtil = get(),
            categoryWorkerUtil = get(),
            menuProductWorkerUtil = get(),
            deliveryWorkerUtil = get(),
            userWorkerUtil = get(),
            orderRepo = get(),
            userInteractor = get(),
            dataStoreRepo = get(),
        )
    } bind IMainInteractor::class
    single {
        UserInteractor(
            userRepo = get(),
            userWorkerUtil = get(),
            dataStoreRepo = get(),
            authRepo = get(),
            orderMapper = get(),
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
            dataStoreRepo = get(),
            productInteractor = get(),
        )
    } bind ICartProductInteractor::class
    single<ICafeInteractor> {
        CafeInteractor(
            cafeRepo = get(),
            dataStoreRepo = get(),
        )
    }
    single<IUpdateInteractor> { UpdateInteractor(versionRepo = get()) }
    single<IOrderInteractor> {
        OrderInteractor(
            orderRepo = get(),
            cartProductRepo = get(),
            dataStoreRepo = get(),
            orderMapper = get(),
            productInteractor = get()
        )
    }
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
    single<ICategoryInteractor> {
        CategoryInteractor(
            categoryRepo = get()
        )
    }
    single<IMenuProductInteractor> {
        MenuProductInteractor(
            menuProductRepo = get(),
        )
    }
    single<IDeferredTimeInteractor> {
        DeferredTimeInteractor()
    }
}
