package com.bunbeauty.profile.ui.screen.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.ui.element.button.MainButton
import com.bunbeauty.designsystem.ui.element.button.SecondaryButton
import com.bunbeauty.designsystem.ui.screen.bottomsheet.FoodDeliveryModalBottomSheet
import com.bunbeauty.profile.presentation.settings.SettingsState
import org.jetbrains.compose.resources.stringResource
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.action_logout_cancel
import papakarlo.designsystem.generated.resources.action_settings_delete
import papakarlo.designsystem.generated.resources.msg_settings_delete

@Composable
fun DisableUserBottomSheetScreen(
    disableUserBottomSheetUI: SettingsViewState.DisableUserBottomSheetUI,
    onAction: (SettingsState.Action) -> Unit,
) {
    FoodDeliveryModalBottomSheet(
        onDismissRequest = {
            onAction(SettingsState.Action.CloseDisableUserBottomSheet)
        },
        isShown = disableUserBottomSheetUI.isShown,
        title = stringResource(Res.string.msg_settings_delete),
    ) {
        MainButton(
            textStringId = Res.string.action_settings_delete,
            elevated = false,
            onClick = {
                onAction(SettingsState.Action.DisableUserBottomSheet)
            },
        )
        SecondaryButton(
            modifier = Modifier.padding(top = 4.dp),
            textStringId = Res.string.action_logout_cancel,
            elevated = false,
            onClick = {
                onAction(SettingsState.Action.CloseDisableUserBottomSheet)
            },
        )
    }
}
