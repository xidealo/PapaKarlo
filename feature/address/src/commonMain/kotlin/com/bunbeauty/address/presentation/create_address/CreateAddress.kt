package com.bunbeauty.address.presentation.create_address

import com.bunbeauty.core.base.BaseAction
import com.bunbeauty.core.base.BaseDataState
import com.bunbeauty.core.base.BaseEvent
import com.bunbeauty.designsystem.ui.element.textfield.SuggestionUi

interface CreateAddress {
    data class DataState(
        val street: String,
        val streetFocused: Boolean,
        val streetSuggestionList: List<SuggestionUi>,
        val isSuggestionLoading: Boolean,
        val selectedStreetSuggestion: SuggestionUi?,
        val hasStreetError: Boolean,
        val house: String,
        val hasHouseError: Boolean,
        val flat: String,
        val entrance: String,
        val floor: String,
        val comment: String,
        val isCreateLoading: Boolean,
    ) : BaseDataState

    data class StreetField(
        val street: String,
        val isFocused: Boolean,
        val selectedSuggestion: SuggestionUi?,
    )

    sealed interface Event : BaseEvent {
        data object SuggestionLoadingFailed : Event

        data object AddressCreatedSuccess : Event

        data object AddressCreatedFailed : Event

        data object Back : Event
    }

    sealed interface Action : BaseAction {
        data object Init : Action

        data class StreetTextChange(
            val street: String,
        ) : Action

        data class StreetFocusChange(
            val isFocused: Boolean,
        ) : Action

        data class SuggestionSelect(
            val suggestion: SuggestionUi,
        ) : Action

        data class HouseTextChange(
            val house: String,
        ) : Action

        data class FlatTextChange(
            val flat: String,
        ) : Action

        data class EntranceTextChange(
            val entrance: String,
        ) : Action

        data class FloorTextChange(
            val floor: String,
        ) : Action

        data class CommentTextChange(
            val comment: String,
        ) : Action

        data object SaveClick : Action

        data object BackClick : Action
    }
}
