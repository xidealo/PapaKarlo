package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.Constants.EMAIL_REQUEST_KEY
import com.bunbeauty.common.Constants.RESULT_EMAIL_KEY
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toSuccessOrEmpty
import com.bunbeauty.domain.enums.OneLineActionType
import com.bunbeauty.domain.model.OneLineActionModel
import com.bunbeauty.domain.model.User
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.di.annotation.Api
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.profile.SettingsFragmentDirections.toLoginFragment
import com.bunbeauty.papakarlo.ui.profile.SettingsFragmentDirections.toOneLineActionBottomSheet
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    @Api private val userRepo: UserRepo,
    private val resourcesProvider: IResourcesProvider,
) : BaseViewModel() {

    private var userEmail: String? = null
    private val mutableUserState: MutableStateFlow<State<User>> = MutableStateFlow(State.Loading())
    val userState: StateFlow<State<User>> = mutableUserState.asStateFlow()

    fun getUser(userUuid: String) {
        userRepo.observeUserByUuid(userUuid).onEach { user ->
            userEmail = user?.email
            mutableUserState.value = user.toSuccessOrEmpty()
        }.launchIn(viewModelScope)
    }

    fun onAddEmailClicked() {
        val oneLineActionModel = OneLineActionModel(
            title = resourcesProvider.getString(R.string.title_settings_add_email),
            infoText = null,
            hint = resourcesProvider.getString(R.string.hint_settings_email),
            type = OneLineActionType.EMAIL,
            inputText = "",
            buttonText = resourcesProvider.getString(R.string.action_settings_save),
            requestKey = EMAIL_REQUEST_KEY,
            resultKey = RESULT_EMAIL_KEY,
        )
        router.navigate(toOneLineActionBottomSheet(oneLineActionModel))
    }

    fun onEditEmailClicked() {
        val oneLineActionModel = OneLineActionModel(
            title = resourcesProvider.getString(R.string.title_settings_edit_email),
            infoText = null,
            hint = resourcesProvider.getString(R.string.hint_settings_email),
            type = OneLineActionType.EMAIL,
            inputText = userEmail ?: "",
            buttonText = resourcesProvider.getString(R.string.action_settings_save),
            requestKey = EMAIL_REQUEST_KEY,
            resultKey = RESULT_EMAIL_KEY,
        )
        router.navigate(toOneLineActionBottomSheet(oneLineActionModel))
    }

    fun onPhoneClicked() {
        router.navigate(toLoginFragment())
    }
}