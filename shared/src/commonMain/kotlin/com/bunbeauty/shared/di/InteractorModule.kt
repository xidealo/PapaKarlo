package com.bunbeauty.shared.di

import com.bunbeauty.shared.domain.interactor.cafe.CafeInteractor
import com.bunbeauty.shared.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.shared.domain.interactor.cart.CartProductInteractor
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.interactor.city.CityInteractor
import com.bunbeauty.shared.domain.interactor.city.ICityInteractor
import com.bunbeauty.shared.domain.interactor.menu_product.IMenuProductInteractor
import com.bunbeauty.shared.domain.interactor.menu_product.MenuProductInteractor
import com.bunbeauty.shared.domain.interactor.order.IOrderInteractor
import com.bunbeauty.shared.domain.interactor.order.OrderInteractor
import com.bunbeauty.shared.domain.interactor.payment.PaymentInteractor
import com.bunbeauty.shared.domain.interactor.product.IProductInteractor
import com.bunbeauty.shared.domain.interactor.product.ProductInteractor
import com.bunbeauty.shared.domain.interactor.update.IUpdateInteractor
import com.bunbeauty.shared.domain.interactor.update.UpdateInteractor
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.interactor.user.UserInteractor
import org.koin.dsl.module

internal fun interactorModule() = module {
    single<IUserInteractor> {
        UserInteractor(
            userRepo = get(),
            dataStoreRepo = get(),
        )
    }
    single<ICityInteractor> {
        CityInteractor(
            dataStoreRepo = get(),
            cityRepo = get()
        )
    }
    single {
        CityInteractor(
            dataStoreRepo = get(),
            cityRepo = get()
        )
    }
    single<ICartProductInteractor> {
        CartProductInteractor(
            cartProductRepo = get(),
            deliveryRepo = get(),
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
            productInteractor = get(),
        )
    }
    single<IMenuProductInteractor> {
        MenuProductInteractor(
            menuProductRepo = get(),
        )
    }
    single<IProductInteractor> {
        ProductInteractor(
            dataStoreRepo = get(),
        )
    }
    single {
        PaymentInteractor(
            dataStoreRepo = get(),
            paymentRepository = get(),
        )
    }
}
