package com.bunbeauty.papakarlo.presentation.profile

import com.bunbeauty.domain.enums.OneLineActionType
import com.bunbeauty.domain.model.OneLineActionModel
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.profile.SettingsFragmentDirections
import javax.inject.Inject


class SettingsViewModel @Inject constructor(
    private val iResourcesProvider: IResourcesProvider
) : ToolbarViewModel() {

    fun gotoChangeEmail(email: String) {
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