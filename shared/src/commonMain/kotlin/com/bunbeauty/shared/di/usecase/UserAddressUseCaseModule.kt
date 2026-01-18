package com.bunbeauty.shared.di.usecase

import com.bunbeauty.core.domain.address.GetSelectableUserAddressListUseCase
import com.bunbeauty.core.domain.address.GetUserAddressListUseCase
import com.bunbeauty.core.domain.address.GetUserAddressListUseCaseImpl
import com.bunbeauty.core.domain.address.SaveSelectedUserAddressUseCase
import org.koin.dsl.module

fun userAddressUseCaseModule() =
    module {
        factory<GetUserAddressListUseCase> {
            GetUserAddressListUseCaseImpl(
                userAddressRepo = get(),
            )
        }
        factory {
            GetSelectableUserAddressListUseCase(
                userAddressRepo = get(),
                getCurrentUserAddressUseCase = get(),
            )
        }
        factory {
            SaveSelectedUserAddressUseCase(
                userAddressRepo = get(),
                userRepo = get(),
            )
        }
    }
