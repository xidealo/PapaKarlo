package com.bunbeauty.address.di

import com.bunbeauty.address.presentation.create_address.CreateAddressViewModel
import com.bunbeauty.address.presentation.user_address_list.UserAddressListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun addressFeatureModule() =
    module {
        viewModel {
            CreateAddressViewModel(
                getSuggestionsUseCase = get(),
                createAddressUseCase = get(),
                saveSelectedUserAddressUseCase = get(),
            )
        }
        viewModel {
            UserAddressListViewModel(
                getSelectableUserAddressListUseCase = get(),
                saveSelectedUserAddressUseCase = get(),
            )
        }
    }
