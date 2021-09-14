package com.bunbeauty.papakarlo.presentation.address

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.Constants.HOUSE_ERROR_KEY
import com.bunbeauty.common.Constants.STREET_ERROR_KEY
import com.bunbeauty.domain.auth.IAuthUtil
import com.bunbeauty.domain.model.Street
import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.StreetRepo
import com.bunbeauty.domain.repo.UserAddressRepo
import com.bunbeauty.domain.util.field_helper.IFieldHelper
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.di.annotation.Api
import com.bunbeauty.papakarlo.di.annotation.Firebase
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreationAddressViewModel @Inject constructor(
    @Api private val userAddressRepo: UserAddressRepo,
    @Firebase private val streetRepo: StreetRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val resourcesProvider: IResourcesProvider,
    private val authUtil: IAuthUtil,
    private val fieldHelper: IFieldHelper
) : BaseViewModel() {

    private var streetList: List<Street> = emptyList()
    private val mutableStreetNameList: MutableStateFlow<List<String>> =
        MutableStateFlow(emptyList())
    val streetNameList: StateFlow<List<String>> = mutableStreetNameList.asStateFlow()

    init {
        getStreets()
    }

    private fun getStreets() {
        viewModelScope.launch {
            streetList = streetRepo.getStreets()
            mutableStreetNameList.value = streetList.map { street ->
                street.name
            }
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
        val selectedStreet = streetList.find { street -> street.name == streetName }
        if (!fieldHelper.isCorrectFieldContent(streetName, true, 50) || selectedStreet == null) {
            sendFieldError(
                STREET_ERROR_KEY,
                resourcesProvider.getString(R.string.error_create_address_street)
            )
            return
        }

        if (!fieldHelper.isCorrectFieldContent(house, true, 5)) {
            sendFieldError(
                HOUSE_ERROR_KEY,
                resourcesProvider.getString(R.string.error_create_address_house)
            )
            return
        }

        viewModelScope.launch {
            val userUuid = authUtil.userUuid
            val userAddress = UserAddress(
                street = selectedStreet.name,
                house = house,
                flat = flat,
                entrance = entrance,
                floor = floor,
                comment = comment,
                streetUuid = selectedStreet.uuid,
                userUuid = userUuid,
            )
            val savedUserAddress = userAddressRepo.saveUserAddress(userAddress)
            dataStoreRepo.saveUserAddressUuid(savedUserAddress.uuid)

            showMessage(resourcesProvider.getString(R.string.msg_create_address_created))
            goBack()
        }
    }
}