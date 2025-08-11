package com.bunbeauty.shared.di

import com.bunbeauty.shared.domain.interactor.cafe.CafeInteractor
import com.bunbeauty.shared.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.shared.domain.interactor.cart.CartProductInteractor
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.interactor.city.CityInteractor
import com.bunbeauty.shared.domain.interactor.city.ICityInteractor
import com.bunbeauty.shared.domain.interactor.menu_product.IMenuProductInteractor
import com.bunbeauty.shared.domain.interactor.menu_product.MenuProductInteractor
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.interactor.user.UserInteractor
import org.koin.dsl.module

internal fun interactorModule() = module {
    single<IUserInteractor> {
        UserInteractor(
            userRepo = get(),
            orderRepo = get(),
            cafeRepo = get(),
            userAddressRepo = get()
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
            getCartTotalFlowUseCase = get(),
            cartProductAdditionRepository = get()
        )
    }
    single<ICafeInteractor> {
        CafeInteractor(
            cafeRepo = get(),
            dataStoreRepo = get()
        )
    }
    single<IMenuProductInteractor> {
        MenuProductInteractor(
            menuProductRepo = get(),
            getMenuProductListUseCase = get()
        )
    }
}
