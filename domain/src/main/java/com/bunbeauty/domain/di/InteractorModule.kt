package com.bunbeauty.domain.di

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
import com.bunbeauty.domain.interactor.settings.ISettingsInteractor
import com.bunbeauty.domain.interactor.settings.SettingsInteractor
import com.bunbeauty.domain.interactor.street.IStreetInteractor
import com.bunbeauty.domain.interactor.street.StreetInteractor
import com.bunbeauty.domain.interactor.update.IUpdateInteractor
import com.bunbeauty.domain.interactor.update.UpdateInteractor
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.domain.interactor.user.UserInteractor
import org.koin.dsl.module

fun interactorModule() = module {
    single<IMainInteractor> {
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
    }
    single<IUserInteractor> {
        UserInteractor(
            userRepo = get(),
            userWorkerUtil = get(),
            dataStoreRepo = get(),
            authRepo = get(),
        )
    }
    single<ICityInteractor> {
        CityInteractor(
            dataStoreRepo = get(),
            cityRepo = get(),
            cafeWorkerUtil = get(),
            streetWorkerUtil = get(),
        )
    }
    single<ICartProductInteractor> {
        CartProductInteractor(
            cartProductRepo = get(),
            dataStoreRepo = get(),
            productInteractor = get(),
        )
    }
    single<ICafeInteractor> {
        CafeInteractor(
            cafeRepo = get(),
            dataStoreRepo = get(),
            dataTimeUtil = get()
        )
    }
    single<IUpdateInteractor> {
        UpdateInteractor(versionRepo = get())
    }
    single<IOrderInteractor> {
        OrderInteractor(
            orderRepo = get(),
            cartProductRepo = get(),
            dataStoreRepo = get(),
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
        DeferredTimeInteractor(
            dateTimeUtil = get(),
            dataStoreRepo = get(),
        )
    }
    single<IProductInteractor> {
        ProductInteractor(
            dataStoreRepo = get(),
        )
    }
    single<ISettingsInteractor> {
        SettingsInteractor(
            cityInteractor = get(),
            userInteractor = get(),
        )
    }
}
