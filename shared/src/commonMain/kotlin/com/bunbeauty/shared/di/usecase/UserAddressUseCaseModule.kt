package com.bunbeauty.shared.di.usecase

import com.bunbeauty.shared.domain.use_case.address.GetSelectableUserAddressListUseCase
import com.bunbeauty.shared.domain.use_case.address.GetUserAddressListUseCase
import com.bunbeauty.shared.domain.use_case.address.SaveSelectedUserAddressUseCase
import org.koin.dsl.module

fun userAddressUseCaseModule() = module {
    factory {
        GetUserAddressListUseCase(
            dataStoreRepo = get(),
            userAddressRepo = get()
        )
    }
    factory {
        GetSelectableUserAddressListUseCase(
            dataStoreRepo = get(),
            userAddressRepo = get(),
            getCurrentUserAddressUseCase = get()
        )
    }
    factory {
        SaveSelectedUserAddressUseCase(
            dataStoreRepo = get(),
            userAddressRepo = get()
        )
    }
}
