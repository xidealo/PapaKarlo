package com.bunbeauty.papakarlo.presentation.address

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.Constants.HOUSE_ERROR_KEY
import com.bunbeauty.common.Constants.STREET_ERROR_KEY
import com.bunbeauty.domain.interactor.address.IAddressInteractor
import com.bunbeauty.domain.interactor.street.IStreetInteractor
import com.bunbeauty.domain.model.Street
import com.bunbeauty.domain.util.validator.ITextValidator
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreationAddressViewModel @Inject constructor(
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
        subscribeOnStreetList()
    }

    private fun subscribeOnStreetList() {
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
        if (!textValidator.isRequiredFieldContentCorrect(streetName, maxLength = 50) ||
            incorrectStreetSelected
        ) {
            sendFieldError(
                STREET_ERROR_KEY,
                resourcesProvider.getString(R.string.error_create_address_street)
            )
            return
        }

        if (!textValidator.isRequiredFieldContentCorrect(house, maxLength = 5)) {
            sendFieldError(
                HOUSE_ERROR_KEY,
                resourcesProvider.getString(R.string.error_create_address_house)
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
                showError(resourcesProvider.getString(R.string.error_create_address_fail))
            } else {
                showMessage(resourcesProvider.getString(R.string.msg_create_address_created))
            }
            goBack()
        }
    }
}