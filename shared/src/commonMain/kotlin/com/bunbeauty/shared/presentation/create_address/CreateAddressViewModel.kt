package com.bunbeauty.shared.presentation.create_address

import com.bunbeauty.shared.domain.feature.address.CreateAddressUseCase
import com.bunbeauty.shared.domain.feature.address.GetSuggestionsUseCase
import com.bunbeauty.shared.domain.model.Suggestion
import com.bunbeauty.shared.domain.use_case.address.SaveSelectedUserAddressUseCase
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.SuggestionUi
import com.bunbeauty.shared.presentation.base.SharedStateViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

private const val LETTER_COUNT_FOR_SUGGESTIONS = 3

class CreateAddressViewModel(
    private val getSuggestionsUseCase: GetSuggestionsUseCase,
    private val createAddressUseCase: CreateAddressUseCase,
    private val saveSelectedUserAddressUseCase: SaveSelectedUserAddressUseCase,
) : SharedStateViewModel<CreateAddress.ViewDataState, CreateAddress.Action, CreateAddress.Event>(
    initDataState = CreateAddress.ViewDataState()
) {

    private var suggestionsJob: Job? = null

    init {
        observeStreetChanges()
    }

    override fun reduce(action: CreateAddress.Action, dataState: CreateAddress.ViewDataState) {
        when (action) {
            is CreateAddress.Action.StreetTextChange -> {
                handleStreetTextChange(street = action.street)
            }

            is CreateAddress.Action.StreetFocusChange -> {
                handleStreetFocusChange(isFocused = action.isFocused)
            }

            is CreateAddress.Action.SuggestionSelect -> {
                handleSuggestionSelect(suggestion = action.suggestion)
            }

            is CreateAddress.Action.HouseTextChange -> {
                handleHouseTextChange(house = action.house)
            }

            is CreateAddress.Action.FlatTextChange -> {
                handleFlatTextChange(flat = action.flat)
            }

            is CreateAddress.Action.EntranceTextChange -> {
                handleEntranceTextChange(entrance = action.entrance)
            }

            is CreateAddress.Action.FloorTextChange -> {
                handleFloorTextChange(floor = action.floor)
            }

            is CreateAddress.Action.CommentTextChange -> {
                handleCommentTextChange(comment = action.comment)
            }

            CreateAddress.Action.SaveClick -> {
                handleSaveClick()
            }
        }
    }

    private fun handleStreetTextChange(street: String) {
        if (street.length < LETTER_COUNT_FOR_SUGGESTIONS) {
            suggestionsJob?.cancel()
            setState {
                copy(
                    street = street,
                    streetSuggestionList = persistentListOf(),
                    isSuggestionLoading = false,
                )
            }
        } else {
            setState {
                copy(street = street)
            }
        }
    }

    private fun handleStreetFocusChange(isFocused: Boolean) {
        suggestionsJob?.cancel()
        setState {
            copy(streetFocused = isFocused)
        }
    }

    private fun handleSuggestionSelect(suggestion: SuggestionUi) {
        suggestionsJob?.cancel()
        setState {
            copy(
                street = suggestion.value,
                streetSuggestionList = persistentListOf(),
                selectedStreetSuggestion = suggestion,
                isSuggestionLoading = false,
                hasStreetError = false,
            )
        }
    }

    private fun handleHouseTextChange(house: String) {
        if (house.isBlank()) {
            setState {
                copy(house = house)
            }
        } else {
            setState {
                copy(
                    house = house,
                    hasHouseError = false,
                )
            }
        }
    }

    private fun handleFlatTextChange(flat: String) {
        setState {
            copy(flat = flat)
        }
    }

    private fun handleEntranceTextChange(entrance: String) {
        setState {
            copy(entrance = entrance)
        }
    }

    private fun handleFloorTextChange(floor: String) {
        setState {
            copy(floor = floor)
        }
    }

    private fun handleCommentTextChange(comment: String) {
        setState {
            copy(comment = comment)
        }
    }

    private fun handleSaveClick() {
        val streetSuggestion = dataState.value.selectedStreetSuggestion
        val hasHouseError = dataState.value.house.isBlank()
        setState {
            copy(
                hasStreetError = streetSuggestion == null,
                hasHouseError = hasHouseError,
            )
        }

        if (streetSuggestion == null || hasHouseError) {
            return
        }

        setState {
            copy(isCreateLoading = true)
        }
        sharedScope.launchSafe(
            block = {
                val userAddress = createAddressUseCase(
                    street = Suggestion(
                        fiasId = streetSuggestion.id,
                        street = streetSuggestion.value,
                    ),
                    house = dataState.value.house,
                    flat = dataState.value.flat,
                    entrance = dataState.value.entrance,
                    floor = dataState.value.floor,
                    comment = dataState.value.comment,
                )

                if (userAddress == null) {
                    showCreationFailed()
                } else {
                    saveSelectedUserAddressUseCase(userAddress.uuid)
                    addEvent {
                        CreateAddress.Event.AddressCreatedSuccess
                    }
                }
            },
            onError = {
                showCreationFailed()
            }
        )
    }

    private fun showCreationFailed() {
        addEvent {
            CreateAddress.Event.AddressCreatedFailed
        }
        setState {
            copy(isCreateLoading = false)
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeStreetChanges() {
        mutableDataState.map { state ->
            CreateAddress.StreetField(
                street = state.street,
                isFocused = state.streetFocused,
                selectedSuggestion = state.selectedStreetSuggestion,
            )
        }.distinctUntilChanged()
            .debounce(1_000)
            .filter { streetField ->
                streetField.isFocused &&
                    (streetField.street != streetField.selectedSuggestion?.value) &&
                    (streetField.street.length >= LETTER_COUNT_FOR_SUGGESTIONS)
            }
            .onEach { (street, _) ->
                requestSuggestions(query = street)
            }.launchIn(sharedScope)
    }

    private fun requestSuggestions(query: String) {
        suggestionsJob?.cancel()
        suggestionsJob = sharedScope.launchSafe(
            block = {
                setState {
                    copy(isSuggestionLoading = true)
                }
                val suggestionList = getSuggestionsUseCase(query = query)
                setState {
                    copy(
                        streetSuggestionList = suggestionList.map { suggestion ->
                            SuggestionUi(
                                id = suggestion.fiasId,
                                value = suggestion.street,
                            )
                        }.toImmutableList(),
                        isSuggestionLoading = false,
                    )
                }
            },
            onError = {
                // TODO handle error
            }
        )
    }


}
