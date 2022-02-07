package com.bunbeauty.papakarlo.feature.address.create_address

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.Constants.COMMENT_ERROR_KEY
import com.bunbeauty.common.Constants.ENTRANCE_ERROR_KEY
import com.bunbeauty.common.Constants.FLAT_ERROR_KEY
import com.bunbeauty.common.Constants.FLOOR_ERROR_KEY
import com.bunbeauty.common.Constants.HOUSE_ERROR_KEY
import com.bunbeauty.common.Constants.STREET_ERROR_KEY
import com.bunbeauty.domain.interactor.address.IAddressInteractor
import com.bunbeauty.domain.interactor.street.IStreetInteractor
import com.bunbeauty.domain.model.Street
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.util.text_validator.ITextValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateAddressViewModel  constructor(
    private val resourcesProvider: IResourcesProvider,
    private val textValidator: ITextValidator,
    private val streetInteractor: IStreetInteractor,
    private val addressInteractor: IAddressInteractor,
) : BaseViewModel() {

    private var streetList: List<Street> = emptyList()
    private val mutableStreetNameList: MutableStateFlow<List<String>> =
        MutableStateFlow(emptyList())
    val streetNameList: StateFlow<List<String>> = mutableStreetNameList.asStateFlow()

    init {
        observeStreetList()
    }

    private fun observeStreetList() {
        streetInteractor.observeStreetList().launchOnEach { streetList ->
            this.streetList = streetList
        }
        streetInteractor.observeStreetNameList().launchOnEach { streetNameList ->
            mutableStreetNameList.value = streetNameList
        }
    }

    fun onCreateAddressClicked(
        streetName: String,
        house: String,
        flat: String,
        entrance: String,
        comment: String,
        floor: String
    ) {
        val incorrectStreetSelected = streetList.none { street -> street.name == streetName }
        if (incorrectStreetSelected) {
            sendFieldError(
                STREET_ERROR_KEY,
                resourcesProvider.getString(R.string.error_create_address_street)
            )
            return
        }

        if (house.isEmpty()) {
            sendFieldError(
                HOUSE_ERROR_KEY,
                resourcesProvider.getString(R.string.error_create_address_house)
            )
            return
        }

        if (!textValidator.isFieldContentCorrect(house, maxLength = 5)) {
            sendFieldError(
                HOUSE_ERROR_KEY,
                resourcesProvider.getString(R.string.error_create_address_max_length) + 5
            )
            return
        }

        if (!textValidator.isFieldContentCorrect(flat, maxLength = 5)) {
            sendFieldError(
                FLAT_ERROR_KEY,
                resourcesProvider.getString(R.string.error_create_address_max_length) + 5
            )
            return
        }

        if (!textValidator.isFieldContentCorrect(entrance, maxLength = 5)) {
            sendFieldError(
                ENTRANCE_ERROR_KEY,
                resourcesProvider.getString(R.string.error_create_address_max_length) + 5
            )
            return
        }

        if (!textValidator.isFieldContentCorrect(floor, maxLength = 5)) {
            sendFieldError(
                FLOOR_ERROR_KEY,
                resourcesProvider.getString(R.string.error_create_address_max_length) + 5
            )
            return
        }

        if (!textValidator.isFieldContentCorrect(comment, maxLength = 100)) {
            sendFieldError(
                COMMENT_ERROR_KEY,
                resourcesProvider.getString(R.string.error_create_address_max_length) + 100
            )
            return
        }

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
}