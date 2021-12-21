package com.bunbeauty.papakarlo.presentation.profile.settings

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.Constants.EMAIL_REQUEST_KEY
import com.bunbeauty.common.Constants.RESULT_EMAIL_KEY
import com.bunbeauty.domain.enums.OneLineActionType
import com.bunbeauty.domain.interactor.city.ICityInteractor
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.domain.model.OneLineActionModel
import com.bunbeauty.domain.model.profile.User
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.presentation.state.State
import com.bunbeauty.papakarlo.presentation.state.toStateSuccess
import com.bunbeauty.papakarlo.presentation.state.toSuccessOrEmpty
import com.bunbeauty.papakarlo.ui.fragment.profile.settings.SettingsFragmentDirections.toCitySelectionBottomSheet
import com.bunbeauty.papakarlo.ui.fragment.profile.settings.SettingsFragmentDirections.toOneLineActionBottomSheet
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val cityInteractor: ICityInteractor,
    private val userInteractor: IUserInteractor,
    private val resourcesProvider: IResourcesProvider,
) : BaseViewModel() {

    private var userValue: User? = null
    private val mutableUserState: MutableStateFlow<State<User>> =
        MutableStateFlow(State.Loading())
    val userState: StateFlow<State<User>> = mutableUserState.asStateFlow()

    private val mutableCityName: MutableStateFlow<String> = MutableStateFlow("")
    val cityName: StateFlow<String> = mutableCityName.asStateFlow()

    init {
        observeSelectedCity()
        observeUser()
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
        viewModelScope.launch {
            val user = userInteractor.updateUserEmail(email ?: "")
            if (user == null) {
                mutableUserState.value = userValue.toSuccessOrEmpty()
                showError(resourcesProvider.getString(R.string.error_settings_email))
            } else {
                mutableUserState.value = user.toStateSuccess()
            }
        }
    }

    fun onCityClicked() {
        router.navigate(toCitySelectionBottomSheet())
    }

    private fun observeSelectedCity() {
        cityInteractor.observeSelectedCity().onEach { city ->
            if (city != null) {
                mutableCityName.value = city.name
            }
        }.launchIn(viewModelScope)
    }

    private fun observeUser() {
        userInteractor.observeUser().onEach { user ->
            userValue = user
            mutableUserState.value = user.toSuccessOrEmpty()
        }.launchIn(viewModelScope)
    }
}