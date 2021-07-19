package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.domain.enums.OneLineActionType
import com.bunbeauty.domain.model.OneLineActionModel
import com.bunbeauty.domain.model.entity.UserEntity
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.profile.SettingsFragmentDirections
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val resourcesProvider: IResourcesProvider,
    private val userRepo: UserRepo
) : BaseViewModel() {

    private val userEntityStateData: MutableStateFlow<State<UserEntity>> = MutableStateFlow(State.Loading())
    val userEntityState: StateFlow<State<UserEntity>>
        get() = userEntityStateData.asStateFlow()

    fun getUser(userId: String) {
        userRepo.getUserAsFlow(userId).onEach { user ->
            if (user == null) {
                userEntityStateData.value = State.Empty()
                return@onEach
            }

            userEntityStateData.value = user.toStateSuccess()

        }.launchIn(viewModelScope)
    }

    fun gotoChangeEmail() {
        val email = (userEntityState.value as State.Success<UserEntity>).data.email
        router.navigate(
            SettingsFragmentDirections.toOneLineActionBottomSheet(
                OneLineActionModel(
                    title = if (email.isEmpty())
                        resourcesProvider.getString(R.string.title_settings_add_email)
                    else
                        resourcesProvider.getString(R.string.title_settings_change_email),
                    type = OneLineActionType.EMAIL,
                    placeholder = "",
                    buttonText = resourcesProvider.getString(R.string.action_settings_save),
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