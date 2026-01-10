package com.bunbeauty.address.ui.screen.useraddresslist

import androidx.compose.runtime.Immutable
import com.bunbeauty.core.base.BaseViewState
import com.bunbeauty.address.ui.model.UserAddressItem

@Immutable
data class UserAddressListViewState(
    val userAddressItems: List<UserAddressItem> = emptyList(),
    val state: State,
) : BaseViewState {
    @Immutable
    sealed interface State {
        data object Loading : State

        data object Error : State

        data object Success : State

        data object Empty : State
    }
}
