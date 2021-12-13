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