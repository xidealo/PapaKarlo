package com.bunbeauty.papakarlo.presentation.address

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.Constants
import com.bunbeauty.domain.model.ui.Street
import com.bunbeauty.domain.model.ui.address.UserAddress
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.StreetRepo
import com.bunbeauty.domain.repo.UserAddressRepo
import com.bunbeauty.domain.util.field_helper.IFieldHelper
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class CreationAddressViewModel @Inject constructor(
    private val userAddressRepo: UserAddressRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val streetRepo: StreetRepo,
    private val resourcesProvider: IResourcesProvider,
    private val fieldHelper: IFieldHelper
) : BaseViewModel() {

    var streets = listOf<Street>()
    var streetNamesFiled = ObservableField<List<String>>()

    fun getStreets() {
        viewModelScope.launch {
            streetRepo.getStreets().collect { streetsFlow ->
                streetNamesFiled.set(streetsFlow.map { it.name })
                streets = streetsFlow
            }
        }
    }

    fun onCreateAddressClicked(
        street: String,
        house: String,
        flat: String,
        entrance: String,
        comment: String,
        floor: String
    ) {
        if (!fieldHelper.isCorrectFieldContent(
                street,
                true,
                50
            ) || streets.find { it.name == street } == null
        ) {
            sendFieldError(
                Constants.STREET_ERROR_KEY,
                resourcesProvider.getString(R.string.error_creation_address_name)
            )
            return
        }

        if (!fieldHelper.isCorrectFieldContent(
                house,
                true,
                5
            )
        ) {
            sendFieldError(
                Constants.HOUSE_ERROR_KEY,
                " "
            )
            return
        }

        if (!fieldHelper.isCorrectFieldContent(
                flat,
                false,
                5
            )
        ) {
            sendFieldError(
                Constants.FLAT_ERROR_KEY,
                " "
            )
            return
        }

        if (!fieldHelper.isCorrectFieldContent(
                entrance,
                false,
                5
            )
        ) {
            sendFieldError(
                Constants.ENTRANCE_ERROR_KEY,
                " "
            )
            return
        }

        if (!fieldHelper.isCorrectFieldContent(
                comment,
                false,
                100
            )
        ) {
            sendFieldError(
                Constants.COMMENT_ERROR_KEY,
                " "
            )
            return
        }

        if (!fieldHelper.isCorrectFieldContent(
                floor,
                false,
                5
            )
        ) {
            sendFieldError(
                Constants.FLOOR_ERROR_KEY,
                " "
            )
            return
        }

        val userAddress = UserAddress().apply {
            this.street = streets.find { it.name == street }
            this.house = house
            this.flat = flat
            this.entrance = entrance
            this.comment = comment
            this.floor = floor
        }

        viewModelScope.launch(Dispatchers.IO) {
            userAddress.userUuid = dataStoreRepo.userUuid.first()
            val uuid = if (!userAddress.userUuid.isNullOrEmpty()) {
                userAddressRepo.insert("token", userAddress).uuid
            } else {
                userAddress.uuid = UUID.randomUUID().toString()
                userAddressRepo.insert(userAddress)
                userAddress.uuid
            }
            dataStoreRepo.saveUserAddressUuid(uuid)
            withContext(Dispatchers.Main) {
                showMessage(resourcesProvider.getString(R.string.msg_creation_address_created_address))
                router.navigateUp()
            }
        }
    }
}