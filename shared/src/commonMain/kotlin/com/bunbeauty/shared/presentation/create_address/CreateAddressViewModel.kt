package com.bunbeauty.shared.presentation.create_address

import com.bunbeauty.shared.domain.asCommonStateFlow
import com.bunbeauty.shared.domain.exeptions.EmptyStreetListException
import com.bunbeauty.shared.domain.interactor.address.CreateAddressUseCase
import com.bunbeauty.shared.domain.interactor.address.SaveSelectedUserAddressUseCase
import com.bunbeauty.shared.domain.interactor.street.GetStreetsUseCase
import com.bunbeauty.shared.presentation.SharedViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateAddressViewModel(
    private val getStreetsUseCase: GetStreetsUseCase,
    private val createAddressUseCase: CreateAddressUseCase,
    private val saveSelectedUserAddressUseCase: SaveSelectedUserAddressUseCase,
) : SharedViewModel() {

    private val mutableStreetListState = MutableStateFlow(CreateAddressState())
    val streetListState = mutableStreetListState.asCommonStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        mutableStreetListState.update {
            it.copy(
                state = CreateAddressState.State.Error(throwable),
                isCreateLoading = false
            )
        }
    }

    fun getStreetList() {

        sharedScope.launch(exceptionHandler) {
            val streets = getStreetsUseCase().ifEmpty {
                mutableStreetListState.update { oldState ->
                    oldState.copy(
                        state = CreateAddressState.State.Error(EmptyStreetListException)
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

    private fun isStreetCorrect(streetText: String): Boolean {
        return streetListState.value
            .streetItemList
            .any { street ->
                street.name == streetText
            }
    }

    fun onStreetTextChanged(streetText: String) {
        if (isStreetCorrect(streetText))
            mutableStreetListState.update { oldState ->
                oldState.copy(
                    hasStreetError = false
                )
            }
    }

    fun hasStreetError(streetText: String): Boolean {
        return !isStreetCorrect(streetText)
    }

    fun hasIncorrectHouseError(houseText: String): CreateAddressState.FieldError? {
        return if (houseText.isEmpty()) {
            CreateAddressState.FieldError.INCORRECT
        } else {
            null
        }
    }

    fun hasHouseMaxLengthError(houseText: String): CreateAddressState.FieldError? {
        return if (houseText.isEmpty()) {
            CreateAddressState.FieldError.LENGTH
        } else {
            null
        }
    }

    fun hasFlatMaxLengthError(flatText: String): Boolean {
        return flatText.length > 5
    }

    fun hasEntranceMaxLengthError(entranceText: String): Boolean {
        return entranceText.length > 5
    }

    fun hasFloorMaxLengthError(floorText: String): Boolean {
        return floorText.length > 5
    }

    fun hasCommentMaxLengthError(commentText: String): Boolean {
        return commentText.length > 100
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
                houseFieldError = null,
                hasFlatError = false,
                hasEntranceError = false,
                hasFloorError = false,
                hasCommentError = false,
                isCreateLoading = true
            )
        }

        mutableStreetListState.update { oldState ->
            oldState.copy(
                hasStreetError = hasStreetError(streetName),
                houseFieldError = hasIncorrectHouseError(house) ?: hasHouseMaxLengthError(house),
                hasFlatError = hasFlatMaxLengthError(flat),
                hasEntranceError = hasEntranceMaxLengthError(entrance),
                hasFloorError = hasFloorMaxLengthError(floor),
                hasCommentError = hasCommentMaxLengthError(comment)
            )
        }

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

}
