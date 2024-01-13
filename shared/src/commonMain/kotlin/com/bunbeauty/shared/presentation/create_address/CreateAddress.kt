package com.bunbeauty.shared.presentation.create_address

import com.bunbeauty.shared.presentation.SuggestionUi
import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseEvent
import com.bunbeauty.shared.presentation.base.BaseViewDataState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface CreateAddress {

    data class ViewDataState(
        val street: String = "",
        val streetFocused: Boolean = false,
        val streetSuggestionList: ImmutableList<SuggestionUi> = persistentListOf(),
        val isSuggestionLoading: Boolean = false,
        val selectedStreetSuggestion: SuggestionUi? = null,
        val hasStreetError: Boolean = false,

        val house: String = "",
        val hasHouseError: Boolean = false,

        val flat: String = "",
        val entrance: String = "",
        val floor: String = "",
        val comment: String = "",

        val isCreateLoading: Boolean = false,
    ) : BaseViewDataState {

         val suggestionListNotEmpty = streetSuggestionList.isNotEmpty()
    }

    data class StreetField(
        val street: String,
        val isFocused: Boolean,
        val selectedSuggestion: SuggestionUi?,
    )

    sealed interface Event : BaseEvent {
        data object AddressCreatedSuccess : Event
        data object AddressCreatedFailed : Event
    }

    sealed interface Action : BaseAction {
        data class StreetTextChange(val street: String): Action
        data class StreetFocusChange(val isFocused: Boolean): Action
        data class SuggestionSelect(val suggestion: SuggestionUi): Action
        data class HouseTextChange(val house: String): Action
        data class FlatTextChange(val flat: String): Action
        data class EntranceTextChange(val entrance: String): Action
        data class FloorTextChange(val floor: String): Action
        data class CommentTextChange(val comment: String): Action
        data object SaveClick: Action
    }

}