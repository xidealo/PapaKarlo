package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.model.local.user.User
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.profile.SettingsFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class SettingsViewModel : ToolbarViewModel() {
    abstract fun logout()
    abstract fun updateUser(user: User)
}

class SettingsViewModelImpl @Inject constructor(
    private val userRepo: UserRepo,
    private val resourcesProvider: IResourcesProvider
) : SettingsViewModel() {

    override fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.Default) {
            userRepo.update(user)
            messageSharedFlow.emit(resourcesProvider.getString(R.string.action_settings_successfully_save))
        }
    }

    override fun logout() {
        router.navigate(SettingsFragmentDirections.toLogoutBottomSheet())
    }
}