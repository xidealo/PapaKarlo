package com.bunbeauty.papakarlo.presentation.profile.settings

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.Constants.EMAIL_REQUEST_KEY
import com.bunbeauty.common.Constants.RESULT_EMAIL_KEY
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toSuccessOrEmpty
import com.bunbeauty.domain.auth.IAuthUtil
import com.bunbeauty.domain.enums.OneLineActionType
import com.bunbeauty.domain.model.City
import com.bunbeauty.domain.model.OneLineActionModel
import com.bunbeauty.domain.model.Profile
import com.bunbeauty.domain.repo.CityRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.di.annotation.Api
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.fragment.profile.settings.SettingsFragmentDirections.*
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    @Api private val userRepo: UserRepo,
    @Api private val cityRepo: CityRepo,
    private val authUtil: IAuthUtil,
    private val dataStoreRepo: DataStoreRepo,
    private val resourcesProvider: IResourcesProvider,
) : BaseViewModel() {

    private var profileValue: Profile? = null
    private val mutableProfileState: MutableStateFlow<State<Profile>> =
        MutableStateFlow(State.Loading())
    val profileState: StateFlow<State<Profile>> = mutableProfileState.asStateFlow()

    private val mutableCity: MutableStateFlow<City?> = MutableStateFlow(null)
    val city: StateFlow<City?> = mutableCity.asStateFlow()

    init {
        subscribeOnSelectedCity()
        subscribeOnProfile()
    }

    fun onEmailClicked() {
        val titleResourceId = if (profileValue?.email.isNullOrEmpty()) {
            R.string.title_settings_add_email
        } else {
            R.string.title_settings_edit_email
        }
        val oneLineActionModel = OneLineActionModel(
            title = resourcesProvider.getString(titleResourceId),
            infoText = null,
            hint = resourcesProvider.getString(R.string.hint_settings_email),
            type = OneLineActionType.EMAIL,
            inputText = profileValue?.email,
            buttonText = resourcesProvider.getString(R.string.action_settings_save),
            requestKey = EMAIL_REQUEST_KEY,
            resultKey = RESULT_EMAIL_KEY,
        )
        router.navigate(toOneLineActionBottomSheet(oneLineActionModel))
    }

    fun onEmailChanged(email: String?) {
        mutableProfileState.value = State.Loading()
        val user = profileValue?.copy(email = email ?: return)
        if (user != null && user.email != profileValue?.email) {
            viewModelScope.launch {
                userRepo.updateUserEmail(user)
                mutableProfileState.value = profileValue.toSuccessOrEmpty()
            }
        }
    }

    fun onPhoneClicked() {
        router.navigate(toLoginFragment())
    }

    fun onCityClicked() {
        router.navigate(toCitySelectionBottomSheet())
    }

    private fun subscribeOnSelectedCity() {
        dataStoreRepo.selectedCityUuid.flatMapLatest { selectedCityUuid ->
            cityRepo.observeCityByUuid(selectedCityUuid ?: "").onEach { city ->
                mutableCity.value = city
            }
        }.launchIn(viewModelScope)
    }

    private fun subscribeOnProfile() {
        authUtil.observeUserUuid().flatMapLatest { userUuid ->
            userRepo.observeUserByUuid(userUuid ?: "").onEach { user ->
                profileValue = user
                mutableProfileState.value = user.toSuccessOrEmpty()
            }
        }.launchIn(viewModelScope)
    }
}