package com.bunbeauty.shared.di

import com.bunbeauty.shared.domain.interactor.cafe.CafeInteractor
import com.bunbeauty.shared.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.shared.domain.interactor.cart.CartProductInteractor
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.core.domain.city.CityInteractor
import com.bunbeauty.core.domain.city.ICityInteractor
import com.bunbeauty.core.domain.menu_product.IMenuProductInteractor
import com.bunbeauty.core.domain.menu_product.MenuProductInteractor
import com.bunbeauty.core.domain.user.IUserInteractor
import com.bunbeauty.core.domain.user.UserInteractor
import org.koin.dsl.module

internal fun interactorModule() =
    module {
        single<IUserInteractor> {
            UserInteractor(
                userRepo = get(),
                orderRepo = get(),
                cafeRepo = get(),
                userAddressRepo = get(),
            )
        }
        single<ICityInteractor> {
            CityInteractor(
                cityRepo = get(),
            )
        }
        single {
            CityInteractor(
                cityRepo = get(),
            )
        }
        single<ICartProductInteractor> {
            CartProductInteractor(
                cartProductRepo = get(),
                getCartTotalFlowUseCase = get(),
                cartProductAdditionRepository = get(),
            )
        }
        single<ICafeInteractor> {
            CafeInteractor(
                cafeRepo = get(),
                dataStoreRepo = get(),
            )
        }
        single<IMenuProductInteractor> {
            MenuProductInteractor(
                menuProductRepo = get(),
                getMenuProductListUseCase = get(),
            )
        }
    }
