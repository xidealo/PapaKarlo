package com.bunbeauty.address.presentation.create_address

import com.bunbeauty.core.base.SharedStateViewModel
import com.bunbeauty.core.domain.address.CreateAddressUseCase
import com.bunbeauty.core.domain.address.GetSuggestionsUseCase
import com.bunbeauty.core.domain.address.SaveSelectedUserAddressUseCase
import com.bunbeauty.core.extension.launchSafe
import com.bunbeauty.core.model.Suggestion
import com.bunbeauty.designsystem.ui.element.textfield.SuggestionUi
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

private const val LETTER_COUNT_FOR_SUGGESTIONS = 3
private const val SUGGESTION_REQUEST_DEBOUNCE_MILLIS = 1_000L

class CreateAddressViewModel(
    private val getSuggestionsUseCase: GetSuggestionsUseCase,
    private val createAddressUseCase: CreateAddressUseCase,
    private val saveSelectedUserAddressUseCase: SaveSelectedUserAddressUseCase,
) : SharedStateViewModel<CreateAddress.DataState, CreateAddress.Action, CreateAddress.Event>(
        initDataState =
            CreateAddress.DataState(
                street = "",
                streetFocused = false,
                streetSuggestionList = listOf(),
                isSuggestionLoading = false,
                selectedStreetSuggestion = null,
                hasStreetError = false,
                house = "",
                hasHouseError = false,
                flat = "",
                entrance = "",
                floor = "",
                comment = "",
                isCreateLoading = false,
            ),
    ) {
    private var suggestionsJob: Job? = null

    init {
        observeStreetChanges()
    }

    override fun reduce(
        action: CreateAddress.Action,
        dataState: CreateAddress.DataState,
    ) {
        when (action) {
            is CreateAddress.Action.Init -> {
                observeStreetChanges()
            }
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

            CreateAddress.Action.BackClick ->
                addEvent {
                    CreateAddress.Event.Back
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
                copy(
                    street = street,
                )
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
                street = suggestion.value + suggestion.postfix,
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
        val hasStreetError = streetSuggestion == null || streetSuggestion.value+streetSuggestion.postfix != dataState.value.street
        val hasHouseError = dataState.value.house.isBlank()
        setState {
            copy(
                hasStreetError = hasStreetError,
                hasHouseError = hasHouseError,
            )
        }

        if (streetSuggestion == null || hasStreetError || hasHouseError) {
            return
        }

        setState {
            copy(isCreateLoading = true)
        }
        sharedScope.launchSafe(
            block = {
                val userAddress =
                    createAddressUseCase(
                        street =
                            Suggestion(
                                fiasId = streetSuggestion.id,
                                street = streetSuggestion.value,
                                details = null,
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
            },
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
        mutableDataState
            .map { state ->
                CreateAddress.StreetField(
                    street = state.street,
                    isFocused = state.streetFocused,
                    selectedSuggestion = state.selectedStreetSuggestion,
                )
            }.distinctUntilChanged()
            .debounce(SUGGESTION_REQUEST_DEBOUNCE_MILLIS)
            .filter { streetField ->
                streetField.isFocused &&
                        (streetField.street != streetField.selectedSuggestion?.value) &&
                        (streetField.street.length >= LETTER_COUNT_FOR_SUGGESTIONS)
            }.onEach { (street, _) ->
                requestSuggestions(query = street)
            }.launchIn(sharedScope)
    }

    private fun requestSuggestions(query: String) {
        suggestionsJob?.cancel()
        suggestionsJob =
            sharedScope.launchSafe(
                block = {
                    setState {
                        copy(isSuggestionLoading = true)
                    }
                    val suggestionList = getSuggestionsUseCase(query = query)
                    setState {
                        copy(
                            streetSuggestionList = suggestionList.map(::mapSuggestion),
                            isSuggestionLoading = false,
                        )
                    }
                },
                onError = {
                    addEvent {
                        CreateAddress.Event.SuggestionLoadingFailed
                    }
                    setState {
                        copy(isSuggestionLoading = false)
                    }
                },
            )
    }

    private fun mapSuggestion(suggestion: Suggestion): SuggestionUi =
        SuggestionUi(
            id = suggestion.fiasId,
            value = suggestion.street,
            postfix =
                suggestion.details?.let { details ->
                    ", $details"
                },
        )
}
