package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.domain.enums.OneLineActionType
import com.bunbeauty.domain.model.OneLineActionModel
import com.bunbeauty.domain.model.local.order.Order
import com.bunbeauty.domain.model.local.user.User
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.profile.SettingsFragmentDirections
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class SettingsViewModel @Inject constructor(
    private val iResourcesProvider: IResourcesProvider,
    private val userRepo: UserRepo
) : ToolbarViewModel() {

    private val userStateData: MutableStateFlow<State<User>> = MutableStateFlow(State.Loading())
    val userState: StateFlow<State<User>>
        get() = userStateData.asStateFlow()

    fun getUser(userId: String) {
        userRepo.getUserAsFlow(userId).onEach { user ->
            if (user == null) {
                userStateData.value = State.Empty()
                return@onEach
            }

            userStateData.value = user.toStateSuccess()

        }.launchIn(viewModelScope)
    }

    fun gotoChangeEmail() {
        val email = (userState.value as State.Success<User>).data.email
        router.navigate(
            SettingsFragmentDirections.toOneLineActionBottomSheet(
                OneLineActionModel(
                    title = if (email.isEmpty())
                        iResourcesProvider.getString(R.string.title_settings_add_email)
                    else
                        iResourcesProvider.getString(R.string.title_settings_change_email),
                    type = OneLineActionType.EMAIL,
                    placeholder = "",
                    buttonText = iResourcesProvider.getString(R.string.action_settings_save),
                    data = if (email.isEmpty())
                        ""
                    else
                        email
                )
            )
        )
    }

    fun logout() {
        router.navigate(SettingsFragmentDirections.toLoginFragment())
    }
}