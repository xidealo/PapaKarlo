package com.bunbeauty.shared.presentation.create_address

import com.bunbeauty.shared.domain.asCommonStateFlow
import com.bunbeauty.shared.domain.exeptions.EmptyStreetListException
import com.bunbeauty.shared.domain.feature.address.GetFilteredStreetListUseCase
import com.bunbeauty.shared.domain.feature.address.CreateAddressUseCase
import com.bunbeauty.shared.domain.use_case.address.SaveSelectedUserAddressUseCase
import com.bunbeauty.shared.domain.feature.address.GetStreetsUseCase
import com.bunbeauty.shared.presentation.SharedViewModel
import com.bunbeauty.shared.presentation.Suggestion
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateAddressViewModel(
    private val getStreetsUseCase: GetStreetsUseCase,
    private val createAddressUseCase: CreateAddressUseCase,
    private val saveSelectedUserAddressUseCase: SaveSelectedUserAddressUseCase,
    private val getFilteredStreetListUseCase: GetFilteredStreetListUseCase,
) : SharedViewModel() {

    private val mutableStreetListState = MutableStateFlow(CreateAddressState())
    val streetListState = mutableStreetListState.asCommonStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        mutableStreetListState.update {
            it.copy(
                state = CreateAddressState.State.Error(throwable),
                isCreateLoading = false
            )
        }
    }

    fun getStreetList() {
        sharedScope.launch(exceptionHandler) {
            mutableStreetListState.update { oldState ->
                oldState.copy(
                    state = CreateAddressState.State.Loading
                )
            }

            val streets = getStreetsUseCase().ifEmpty {
                mutableStreetListState.update { oldState ->
                    oldState.copy(
                        state = CreateAddressState.State.Error(EmptyStreetListException())
                    )
                }
                return@launch
            }

            mutableStreetListState.update { oldState ->
                oldState.copy(
                    streetList = streets,
                    state = CreateAddressState.State.Success
                )
            }
        }
    }

    fun onStreetTextChanged(streetText: String) {
        mutableStreetListState.update { state ->
            state.copy(
                street = streetText,
                suggestedStreetList = getFilteredStreetListUseCase(
                    query = streetText,
                    streetList = state.streetList
                ).map { street ->
                    Suggestion(
                        id = street.uuid,
                        value = street.name,
                    )
                }
            )
        }
        if (isStreetCorrect(streetText)) {
            mutableStreetListState.update { oldState ->
                oldState.copy(hasStreetError = false)
            }
        }
    }

    fun onSuggestedStreetSelected(suggestion: Suggestion) {
        mutableStreetListState.update { state ->
            state.copy(
                street = suggestion.value,
                suggestedStreetList = emptyList()
            )
        }
        if (isStreetCorrect(suggestion.value)) {
            mutableStreetListState.update { oldState ->
                oldState.copy(hasStreetError = false)
            }
        }
    }

    fun onHouseTextChanged(houseText: String) {
        mutableStreetListState.update { state ->
            state.copy(house = houseText)
        }
        if (houseText.isNotBlank()) {
            mutableStreetListState.update { state ->
                state.copy(hasHouseError = false)
            }
        }
    }

    fun onFlatTextChanged(flatText: String) {
        mutableStreetListState.update { state ->
            state.copy(flat = flatText)
        }
    }

    fun onEntranceTextChanged(entranceText: String) {
        mutableStreetListState.update { state ->
            state.copy(entrance = entranceText)
        }
    }

    fun onFloorTextChanged(floorText: String) {
        mutableStreetListState.update { state ->
            state.copy(floor = floorText)
        }
    }

    fun onCommentTextChanged(commentText: String) {
        mutableStreetListState.update { state ->
            state.copy(comment = commentText)
        }
    }

    fun onCreateAddressClicked() {
        mutableStreetListState.update { state ->
            var newState = state.copy(
                hasStreetError = !isStreetCorrect(state.street),
                hasHouseError = state.house.isBlank()
            )
            newState = newState.copy(isCreateLoading = !newState.hasError)
            newState
        }
        if (streetListState.value.hasError) {
            return
        }

        sharedScope.launch(exceptionHandler) {
            val userAddress = createAddressUseCase(
                mutableStreetListState.value.street,
                mutableStreetListState.value.house,
                mutableStreetListState.value.flat,
                mutableStreetListState.value.entrance,
                mutableStreetListState.value.comment,
                mutableStreetListState.value.floor,
            )

            if (userAddress == null) {
                mutableStreetListState.update { state ->
                    state.copy(
                        eventList = state.eventList + CreateAddressState.Event.AddressCreatedFailed,
                        isCreateLoading = false
                    )
                }
            } else {
                saveSelectedUserAddressUseCase(userAddress.userUuid)
                mutableStreetListState.update { state ->
                    state + CreateAddressState.Event.AddressCreatedSuccess
                }
            }
        }
    }

    fun consumeEventList(eventList: List<CreateAddressState.Event>) {
        mutableStreetListState.update { state ->
            state.copy(eventList = state.eventList - eventList.toSet())
        }
    }

    private fun isStreetCorrect(streetText: String): Boolean {
        return streetListState.value
            .streetList
            .any { street ->
                street.name == streetText
            }
    }


}
