package com.bunbeauty.papakarlo.feature.address.create_address

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.address.IAddressInteractor
import com.bunbeauty.domain.interactor.street.IStreetInteractor
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.util.text_validator.ITextValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateAddressViewModel(
    private val textValidator: ITextValidator,
    private val streetInteractor: IStreetInteractor,
    private val addressInteractor: IAddressInteractor,
) : BaseViewModel() {

    private val mutableStreetListState: MutableStateFlow<State<List<StreetItemModel>>> =
        MutableStateFlow(State.Loading())
    val streetListState: StateFlow<State<List<StreetItemModel>>> =
        mutableStreetListState.asStateFlow()

    fun getStreetList() {
        viewModelScope.launch {
            mutableStreetListState.value = streetInteractor.getStreetList()?.map { street ->
                StreetItemModel(
                    uuid = street.uuid,
                    name = street.name,
                    cityUuid = street.cityUuid,
                )
            }.toState(resourcesProvider.getString(R.string.error_create_address_loading))
        }
    }

    fun checkStreetError(streetText: String): Int? {
        return (mutableStreetListState.value as? State.Success)?.let {
            it.data.none { street ->
                street.name == streetText
            }.let { isIncorrectStreetSelected ->
                if (isIncorrectStreetSelected) {
                    R.string.error_create_address_street
                } else {
                    null
                }
            }
        }
    }

    fun checkHouseError(houseText: String): Int? {
        return if (houseText.isEmpty()) {
            R.string.error_create_address_house
        } else {
            null
        }
    }

    fun checkHouseMaxLengthError(houseText: String): Int? {
        return checkMaxLengthError(
            text = houseText,
            maxLength = 5,
            errorId = R.string.error_create_address_house_max_length
        )
    }

    fun checkFlatMaxLengthError(flatText: String): Int? {
        return checkMaxLengthError(
            text = flatText,
            maxLength = 5,
            errorId = R.string.error_create_address_flat_max_length
        )
    }

    fun checkEntranceMaxLengthError(entranceText: String): Int? {
        return checkMaxLengthError(
            text = entranceText,
            maxLength = 5,
            errorId = R.string.error_create_address_entrance_max_length
        )
    }

    fun checkFloorMaxLengthError(floorText: String): Int? {
        return checkMaxLengthError(
            text = floorText,
            maxLength = 5,
            errorId = R.string.error_create_address_floor_max_length
        )
    }

    fun checkCommentMaxLengthError(floorText: String): Int? {
        return checkMaxLengthError(
            text = floorText,
            maxLength = 100,
            errorId = R.string.error_create_address_comment_max_length
        )
    }

    fun onCreateAddressClicked(
        streetName: String,
        house: String,
        flat: String,
        entrance: String,
        floor: String,
        comment: String,
    ) {
        viewModelScope.launch {
            val userAddress = addressInteractor.createAddress(
                streetName,
                house,
                flat,
                entrance,
                comment,
                floor,
            )
            if (userAddress == null) {
                showError(resourcesProvider.getString(R.string.error_create_address_fail), false)
            } else {
                showMessage(resourcesProvider.getString(R.string.msg_create_address_created), false)
            }
            goBack()
        }
    }

    private fun checkMaxLengthError(text: String, maxLength: Int, @StringRes errorId: Int): Int? {
        return if (textValidator.isFieldContentCorrect(text, maxLength = maxLength)) {
            null
        } else {
            errorId
        }
    }
}