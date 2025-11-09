package com.bunbeauty.shared.ui.screen.profile.screen.logout

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.unit.dp
import papakarlo.shared.generated.resources.Res
import com.bunbeauty.shared.ui.common.ui.element.button.MainButton
import com.bunbeauty.shared.ui.common.ui.screen.bottomsheet.FoodDeliveryModalBottomSheet
import com.bunbeauty.shared.ui.screen.profile.screen.settings.SettingsViewState
import com.bunbeauty.shared.presentation.settings.SettingsState
import com.bunbeauty.shared.ui.common.ui.element.button.SecondaryButton
import papakarlo.shared.generated.resources.action_logout
import papakarlo.shared.generated.resources.action_logout_cancel
import papakarlo.shared.generated.resources.title_logout

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
        title = stringResource(Res.string.title_logout),
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
