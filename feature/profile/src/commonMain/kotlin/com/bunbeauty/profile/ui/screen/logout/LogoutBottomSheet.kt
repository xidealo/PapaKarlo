package com.bunbeauty.profile.ui.screen.logout

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.ui.element.button.MainButton
import com.bunbeauty.designsystem.ui.element.button.SecondaryButton
import com.bunbeauty.designsystem.ui.screen.bottomsheet.FoodDeliveryModalBottomSheet
import com.bunbeauty.profile.presentation.settings.SettingsState
import com.bunbeauty.profile.ui.screen.settings.SettingsViewState
import org.jetbrains.compose.resources.stringResource
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.action_logout
import papakarlo.designsystem.generated.resources.action_logout_cancel
import papakarlo.designsystem.generated.resources.title_logout

@Composable
fun LogoutBottomSheetScreen(
    logoutUI: SettingsViewState.LogoutBottomSheetUI,
    onAction: (SettingsState.Action) -> Unit,
) {
    FoodDeliveryModalBottomSheet(
        onDismissRequest = {
            onAction(SettingsState.Action.CloseLogoutBottomSheet)
        },
        isShown = logoutUI.isShown,
        title = stringResource(resource = Res.string.title_logout),
    ) {
        MainButton(
            textStringId = Res.string.action_logout,
            elevated = false,
            onClick = {
                onAction(SettingsState.Action.OnLogoutConfirmClicked)
            },
        )
        SecondaryButton(
            modifier = Modifier.padding(top = 4.dp),
            textStringId = Res.string.action_logout_cancel,
            elevated = false,
            onClick = {
                onAction(SettingsState.Action.CloseLogoutBottomSheet)
            },
        )
    }
}
