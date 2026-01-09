package com.bunbeauty.shared.ui.screen.address.screen.createaddress

import androidx.compose.runtime.Immutable
import com.bunbeauty.designsystem.ui.element.textfield.SuggestionUi
import com.bunbeauty.core.base.BaseViewState
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.StringResource

@Immutable
data class CreateAddressViewState(
    val street: String,
    val streetErrorStringId: StringResource?,
    val streetSuggestionList: ImmutableList<SuggestionUi>,
    val isSuggestionLoading: Boolean,
    val house: String,
    val houseErrorStringId: StringResource?,
    val flat: String,
    val entrance: String,
    val floor: String,
    val comment: String,
    val isCreateLoading: Boolean,
) : BaseViewState {
    val suggestionListNotEmpty = streetSuggestionList.isNotEmpty()
}
