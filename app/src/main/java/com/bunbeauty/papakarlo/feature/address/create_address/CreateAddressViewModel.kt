package com.bunbeauty.papakarlo.feature.address.create_address

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.address.IAddressInteractor
import com.bunbeauty.domain.interactor.street.IStreetInteractor
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.util.text_validator.ITextValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateAddressViewModel constructor(
    private val resourcesProvider: IResourcesProvider,
    private val textValidator: ITextValidator,
    private val streetInteractor: IStreetInteractor,
    private val addressInteractor: IAddressInteractor,
) : BaseViewModel() {

    private val mutableStreetList: MutableStateFlow<List<StreetItemModel>> =
        MutableStateFlow(emptyList())
    val streetList: StateFlow<List<StreetItemModel>> = mutableStreetList.asStateFlow()

    init {
        observeStreetList()
    }

    fun checkStreetError(streetText: String): Int? {
        return mutableStreetList.value.none { street ->
            street.name == streetText
        }.let { isIncorrectStreetSelected ->
            if (isIncorrectStreetSelected) {
                R.string.error_create_address_street
            } else {
                null
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

//        val incorrectStreetSelected =
//            mutableStreetList.value.none { street -> street.name == streetName }
//        if (incorrectStreetSelected) {
//            mutableStreetError.launchEmit(R.string.error_create_address_street)
////            sendFieldError(
////                STREET_ERROR_KEY,
////                resourcesProvider.getString(R.string.error_create_address_street)
////            )
//            return
//        }
//
//        if (house.isEmpty()) {
//            sendFieldError(
//                HOUSE_ERROR_KEY,
//                resourcesProvider.getString(R.string.error_create_address_house)
//            )
//            return
//        }
//
//        if (!textValidator.isFieldContentCorrect(house, maxLength = 5)) {
//            sendFieldError(
//                HOUSE_ERROR_KEY,
//                resourcesProvider.getString(R.string.error_create_address_max_length) + 5
//            )
//            return
//        }
//
//        if (!textValidator.isFieldContentCorrect(flat, maxLength = 5)) {
//            sendFieldError(
//                FLAT_ERROR_KEY,
//                resourcesProvider.getString(R.string.error_create_address_max_length) + 5
//            )
//            return
//        }
//
//        if (!textValidator.isFieldContentCorrect(entrance, maxLength = 5)) {
//            sendFieldError(
//                ENTRANCE_ERROR_KEY,
//                resourcesProvider.getString(R.string.error_create_address_max_length) + 5
//            )
//            return
//        }
//
//        if (!textValidator.isFieldContentCorrect(floor, maxLength = 5)) {
//            sendFieldError(
//                FLOOR_ERROR_KEY,
//                resourcesProvider.getString(R.string.error_create_address_max_length) + 5
//            )
//            return
//        }
//
//        if (!textValidator.isFieldContentCorrect(comment, maxLength = 100)) {
//            sendFieldError(
//                COMMENT_ERROR_KEY,
//                resourcesProvider.getString(R.string.error_create_address_max_length) + 100
//            )
//            return
//        }

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

    private fun observeStreetList() {
        streetInteractor.observeStreetList().launchOnEach { streetList ->
            mutableStreetList.value = streetList.map { street ->
                StreetItemModel(
                    uuid = street.uuid,
                    name = street.name,
                    cityUuid = street.cityUuid,
                )
            }
        }
    }
}