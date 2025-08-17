package com.bunbeauty.papakarlo.feature.profile.screen.logout

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.element.button.SecondaryButton
import com.bunbeauty.papakarlo.common.ui.screen.bottomsheet.FoodDeliveryModalBottomSheet
import com.bunbeauty.papakarlo.feature.profile.screen.settings.SettingsViewState
import com.bunbeauty.shared.presentation.settings.SettingsState

@Composable
fun LogoutBottomSheetScreen(
    logoutUI: SettingsViewState.LogoutBottomSheetUI,
    onAction: (SettingsState.Action) -> Unit
) {
    FoodDeliveryModalBottomSheet(
        onDismissRequest = {
            onAction(SettingsState.Action.CloseLogoutBottomSheet)
        },
        isShown = logoutUI.isShown,
        title = stringResource(R.string.title_logout)
    ) {
        MainButton(
            textStringId = R.string.action_logout,
            elevated = false,
            onClick = {
                onAction(SettingsState.Action.OnLogoutConfirmClicked)
            }
        )
        SecondaryButton(
            modifier = Modifier.padding(top = 4.dp),
            textStringId = R.string.action_logout_cancel,
            elevated = false,
            onClick = {
                onAction(SettingsState.Action.CloseLogoutBottomSheet)
            }
        )
    }
}
