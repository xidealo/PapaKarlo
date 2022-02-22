package com.bunbeauty.papakarlo.feature.profile.settings

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.Constants.EMAIL_REQUEST_KEY
import com.bunbeauty.common.Constants.RESULT_EMAIL_KEY
import com.bunbeauty.domain.interactor.settings.ISettingsInteractor
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.domain.model.profile.Settings
import com.bunbeauty.domain.model.profile.User
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.extensions.toStateSuccess
import com.bunbeauty.papakarlo.extensions.toSuccessOrEmpty
import com.bunbeauty.papakarlo.feature.edit_text.EditTextSettings
import com.bunbeauty.papakarlo.feature.edit_text.EditTextType
import com.bunbeauty.papakarlo.feature.profile.settings.SettingsFragmentDirections.toCitySelectionBottomSheet
import com.bunbeauty.papakarlo.feature.profile.settings.SettingsFragmentDirections.toOneLineActionBottomSheet
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val userInteractor: IUserInteractor,
    private val resourcesProvider: IResourcesProvider,
    private val settingsInteractor: ISettingsInteractor,
) : BaseViewModel() {

    private val mutableSettingsState: MutableStateFlow<State<Settings>> =
        MutableStateFlow(State.Loading())
    val settingsState: StateFlow<State<Settings>> = mutableSettingsState.asStateFlow()

    init {
        observeSettings()
    }

    fun onAddEmailClicked() {
        navigateToEmail(R.string.title_settings_add_email, null)
    }

    fun onEditEmailClicked(email: String) {
        navigateToEmail(R.string.title_settings_edit_email, email)
    }

    fun onCityClicked() {
        router.navigate(toCitySelectionBottomSheet())
    }

    fun onEmailChanged(email: String?) {
        val settingsState = mutableSettingsState.value
        mutableSettingsState.value = State.Loading()
        viewModelScope.launch {
            if (settingsState is State.Success) {
                val user = userInteractor.updateUserEmail(email ?: "")
                if (user == null) {
                    onEmailChangingFailed(settingsState)
                } else {
                    onEmailChangedSuccessful(settingsState, user)
                }
            } else {
                onEmailChangingFailed(settingsState)
            }
        }
    }

    private fun onEmailChangedSuccessful(settingsState: State.Success<Settings>, user: User) {
        mutableSettingsState.value = settingsState.data.copy(user = user).toStateSuccess()
        showMessage(resourcesProvider.getString(R.string.msg_settings_email_updated), false)
    }

    private fun onEmailChangingFailed(settingsState: State<Settings>) {
        mutableSettingsState.value = settingsState
        showError(resourcesProvider.getString(R.string.error_settings_email), false)
    }

    private fun navigateToEmail(@StringRes titleResourceId: Int, email: String?) {
        val oneLineActionModel = EditTextSettings(
            title = resourcesProvider.getString(titleResourceId),
            infoText = null,
            hint = resourcesProvider.getString(R.string.hint_settings_email),
            type = EditTextType.EMAIL,
            inputText = email,
            buttonText = resourcesProvider.getString(R.string.action_settings_save),
            requestKey = EMAIL_REQUEST_KEY,
            resultKey = RESULT_EMAIL_KEY,
        )
        router.navigate(toOneLineActionBottomSheet(oneLineActionModel))
    }

    private fun observeSettings() {
        settingsInteractor.observeSettings().launchOnEach { settings ->
            mutableSettingsState.value = settings.toSuccessOrEmpty()
        }
    }
}