package com.bunbeauty.shared.presentation.create_address

import com.bunbeauty.shared.domain.asCommonStateFlow
import com.bunbeauty.shared.domain.interactor.address.CreateAddressUseCase
import com.bunbeauty.shared.domain.interactor.address.SaveSelectedUserAddressUseCase
import com.bunbeauty.shared.domain.interactor.street.GetStreetsUseCase
import com.bunbeauty.shared.presentation.SharedViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateAddressViewModel(
    private val getStreetsUseCase: GetStreetsUseCase,
    private val createAddressUseCase: CreateAddressUseCase,
    private val saveSelectedUserAddressUseCase: SaveSelectedUserAddressUseCase,
) : SharedViewModel() {

    private val mutableStreetListState: MutableStateFlow<CreateAddressState> =
        MutableStateFlow(CreateAddressState())
    val streetListState: StateFlow<CreateAddressState> =
        mutableStreetListState.asCommonStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        mutableStreetListState.update {
            it.copy(
                state = CreateAddressState.State.Error(throwable)
            )
        }
    }

    fun getStreetList() {
        sharedScope.launch(exceptionHandler) {
            val streets = getStreetsUseCase().ifEmpty {
                mutableStreetListState.update { oldState ->
                    oldState.copy(
                        state = CreateAddressState.State.Empty
                    )
                }
                return@launch
            }

            mutableStreetListState.update { oldState ->
                oldState.copy(
                    streetItemList = streets.map { street ->
                        CreateAddressState.StreetItem(
                            uuid = street.uuid,
                            name = street.name,
                        )
                    },
                    state = CreateAddressState.State.Success
                )
            }
        }
    }

    private fun isCorrect(streetText: String): Boolean {
        return streetListState.value
            .streetItemList
            .any { street ->
                street.name == streetText
            }
    }

    fun onStreetTextChanged(streetText: String) {
        if (isCorrect(streetText))
            mutableStreetListState.update { oldState ->
                oldState.copy(
                    hasStreetError = false
                )
            }
    }

    fun checkStreetError(streetText: String) {
        if (!isCorrect(streetText))
            mutableStreetListState.update { oldState ->
                oldState.copy(
                    hasStreetError = true
                )
            }
    }

    fun checkHouseError(houseText: String) {
        if (houseText.isEmpty()) {
            mutableStreetListState.update { oldState ->
                oldState.copy(
                    hasHouseError = CreateAddressState.FieldError.INCORRECT
                )
            }
        }
    }

    fun checkHouseMaxLengthError(houseText: String) {
        if (houseText.length > 5) {
            mutableStreetListState.update { oldState ->
                oldState.copy(
                    hasHouseError = CreateAddressState.FieldError.LENGTH
                )
            }
        }
    }

    fun checkFlatMaxLengthError(flatText: String) {
        if (flatText.length > 5) {
            mutableStreetListState.update { oldState ->
                oldState.copy(
                    hasFlatError = true
                )
            }
        }
    }

    fun checkEntranceMaxLengthError(entranceText: String) {
        if (entranceText.length > 5) {
            mutableStreetListState.update { oldState ->
                oldState.copy(
                    hasEntranceError = true
                )
            }
        }
    }

    fun checkFloorMaxLengthError(floorText: String) {
        if (floorText.length > 5) {
            mutableStreetListState.update { oldState ->
                oldState.copy(
                    hasFloorError = true
                )
            }
        }
    }

    fun checkCommentMaxLengthError(commentText: String) {
        if (commentText.length > 100) {
            mutableStreetListState.update { oldState ->
                oldState.copy(
                    hasCommentError = true
                )
            }
        }
    }

    fun onCreateAddressClicked(
        streetName: String,
        house: String,
        flat: String,
        entrance: String,
        floor: String,
        comment: String,
    ) {
        mutableStreetListState.update { oldState ->
            oldState.copy(
                hasStreetError = false,
                hasHouseError = null,
                hasFlatError = false,
                hasEntranceError = false,
                hasFloorError = false,
                hasCommentError = false,
                isCreateLoading = true
            )
        }

        checkStreetError(streetName)
        checkHouseError(house)
        checkHouseMaxLengthError(house)
        checkFlatMaxLengthError(flat)
        checkEntranceMaxLengthError(entrance)
        checkFloorMaxLengthError(floor)
        checkCommentMaxLengthError(comment)

        if (streetListState.value.hasError) {
            mutableStreetListState.update { oldState ->
                oldState.copy(
                    isCreateLoading = false
                )
            }
            return
        }

        sharedScope.launch(exceptionHandler) {
            val userAddress = createAddressUseCase(
                streetName,
                house,
                flat,
                entrance,
                comment,
                floor,
            )

            if (userAddress == null) {
                mutableStreetListState.update { state ->
                    state.copy(
                        eventList = state.eventList + CreateAddressState.Event.AddressCreatedFailed,
                        isCreateLoading = false
                    )
                }
            } else {
                saveSelectedUserAddressUseCase.invoke(userAddress.userUuid)
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

}
