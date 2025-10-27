package com.bunbeauty.papakarlo.feature.address.screen.createaddress

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.bunbeauty.shared.presentation.SuggestionUi
import com.bunbeauty.shared.presentation.base.BaseViewState
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class CreateAddressViewState(
    val street: String,
    @StringRes val streetErrorStringId: Int?,
    val streetSuggestionList: ImmutableList<SuggestionUi>,
    val isSuggestionLoading: Boolean,
    val house: String,
    @StringRes val houseErrorStringId: Int?,
    val flat: String,
    val entrance: String,
    val floor: String,
    val comment: String,
    val isCreateLoading: Boolean,
) : BaseViewState {
    val suggestionListNotEmpty = streetSuggestionList.isNotEmpty()
}
