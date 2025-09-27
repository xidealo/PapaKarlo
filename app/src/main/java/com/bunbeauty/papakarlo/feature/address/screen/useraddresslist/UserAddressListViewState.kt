package com.bunbeauty.papakarlo.feature.address.screen.useraddresslist

import androidx.compose.runtime.Immutable
import com.bunbeauty.papakarlo.feature.address.model.UserAddressItem
import com.bunbeauty.shared.presentation.base.BaseViewState

@Immutable
data class UserAddressListViewState(
    val userAddressItems: List<UserAddressItem> = emptyList(),
    val state: State
) : BaseViewState {
    @Immutable
    sealed interface State {
        data object Loading : State
        data object Error : State
        data object Success : State
        data object Empty : State
    }
}
