package com.bunbeauty.papakarlo.feature.profile.screen.aboutapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.BuildConfig
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.ComposeBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.card.StartIconCard
import com.bunbeauty.papakarlo.common.ui.screen.bottomsheet.FoodDeliveryBottomSheet
import com.bunbeauty.papakarlo.common.ui.screen.bottomsheet.FoodDeliveryModalBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.profile.screen.profile.ProfileViewState
import com.bunbeauty.papakarlo.feature.profile.screen.settings.SettingsViewState
import com.bunbeauty.shared.presentation.profile.ProfileState
import com.bunbeauty.shared.presentation.settings.SettingsState

@Composable
fun AboutAppBottomSheet(
    aboutBottomSheetUI: ProfileViewState.AboutBottomSheetUI,
    onAction: (ProfileState.Action) -> Unit
) {
    FoodDeliveryModalBottomSheet(
        onDismissRequest = {
            onAction(ProfileState.Action.CloseAboutAppBottomSheet)
        },
        isShown = aboutBottomSheetUI.isShown,
        title = stringResource(R.string.title_about_app)
    ) {
        AboutAppScreen()
    }
}
@Composable
private fun AboutAppScreen() {
    Column{
        StartIconCard(
            label = stringResource(R.string.msg_about_app_developer),
            iconId = R.drawable.ic_bb,
            clickable = false,
            elevated = false,
            iconTint = FoodDeliveryTheme.colors.bunBeautyBrandColor
        )
        StartIconCard(
            modifier = Modifier.padding(top = 8.dp),
            label = stringResource(R.string.msg_about_app_version) + BuildConfig.VERSION_NAME,
            iconId = R.drawable.ic_version,
            clickable = false,
            elevated = false
        )
    }
}

@Preview
@Composable
private fun AboutAppScreenPreview() {
    FoodDeliveryTheme {
        AboutAppScreen()
    }
}
