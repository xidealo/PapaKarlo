package com.bunbeauty.shared.ui.screen.profile.screen.aboutapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.ui.element.card.StartIconCard
import com.bunbeauty.designsystem.ui.screen.bottomsheet.FoodDeliveryModalBottomSheet
import com.bunbeauty.shared.presentation.profile.ProfileState
import com.bunbeauty.shared.ui.screen.profile.screen.profile.ProfileViewState
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.shared.generated.resources.Res
import papakarlo.shared.generated.resources.ic_bb
import papakarlo.shared.generated.resources.ic_version
import papakarlo.shared.generated.resources.msg_about_app_developer
import papakarlo.shared.generated.resources.msg_about_app_version
import papakarlo.shared.generated.resources.title_about_app

@Composable
fun AboutAppBottomSheet(
    aboutBottomSheetUI: ProfileViewState.AboutBottomSheetUI,
    onAction: (ProfileState.Action) -> Unit,
    appVersion: String,
) {
    FoodDeliveryModalBottomSheet(
        onDismissRequest = {
            onAction(ProfileState.Action.CloseAboutAppBottomSheet)
        },
        isShown = aboutBottomSheetUI.isShown,
        title = stringResource(Res.string.title_about_app),
    ) {
        AboutAppScreen(appVersion = appVersion)
    }
}

@Composable
private fun AboutAppScreen(appVersion: String) {
    Column {
        StartIconCard(
            label = stringResource(Res.string.msg_about_app_developer),
            iconId = Res.drawable.ic_bb,
            clickable = false,
            elevated = false,
            iconTint = FoodDeliveryTheme.colors.bunBeautyBrandColor,
        )
        StartIconCard(
            modifier = Modifier.padding(top = 8.dp),
            label = stringResource(Res.string.msg_about_app_version) + appVersion,
            iconId = Res.drawable.ic_version,
            clickable = false,
            elevated = false,
        )
    }
}

@Preview
@Composable
private fun AboutAppScreenPreview() {
    FoodDeliveryTheme {
        AboutAppScreen("1.6.0")
    }
}
