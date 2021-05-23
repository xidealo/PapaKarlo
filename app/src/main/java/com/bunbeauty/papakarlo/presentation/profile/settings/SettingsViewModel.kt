package com.bunbeauty.papakarlo.presentation.profile.settings

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.Constants.EMAIL_REQUEST_KEY
import com.bunbeauty.common.Constants.RESULT_EMAIL_KEY
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toSuccessOrEmpty
import com.bunbeauty.domain.enums.OneLineActionType
import com.bunbeauty.domain.model.City
import com.bunbeauty.domain.model.OneLineActionModel
import com.bunbeauty.domain.model.User
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
    private val dataStoreRepo: DataStoreRepo,
    private val resourcesProvider: IResourcesProvider,
) : BaseViewModel() {

    private var userValue: User? = null
    private val mutableUserState: MutableStateFlow<State<User>> = MutableStateFlow(State.Loading())
    val userState: StateFlow<State<User>> = mutableUserState.asStateFlow()

    private val mutableCity: MutableStateFlow<City?> = MutableStateFlow(null)
    val city: StateFlow<City?> = mutableCity.asStateFlow()

    init {
        subscribeOnSelectedCity()
    }

    fun getUser(userUuid: String) {
        userRepo.observeUserByUuid(userUuid).onEach { user ->
            userValue = user
            mutableUserState.value = user.toSuccessOrEmpty()
        }.launchIn(viewModelScope)
    }

    fun onEmailClicked() {
        val titleResourceId = if (userValue?.email.isNullOrEmpty()) {
            R.string.title_settings_add_email
        } else {
            R.string.title_settings_edit_email
        }
        val oneLineActionModel = OneLineActionModel(
            title = resourcesProvider.getString(titleResourceId),
            infoText = null,
            hint = resourcesProvider.getString(R.string.hint_settings_email),
            type = OneLineActionType.EMAIL,
            inputText = userValue?.email,
            buttonText = resourcesProvider.getString(R.string.action_settings_save),
            requestKey = EMAIL_REQUEST_KEY,
            resultKey = RESULT_EMAIL_KEY,
        )
        router.navigate(toOneLineActionBottomSheet(oneLineActionModel))
    }

    fun onEmailChanged(email: String?) {
        mutableUserState.value = State.Loading()
        val user = userValue?.copy(email = email ?: return)
        if (user != null && user.email != userValue?.email) {
            viewModelScope.launch {
                userRepo.updateUserEmail(user)
                mutableUserState.value = userValue.toSuccessOrEmpty()
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
}