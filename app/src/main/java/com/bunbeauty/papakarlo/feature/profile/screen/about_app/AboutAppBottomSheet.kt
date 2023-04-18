package com.bunbeauty.papakarlo.feature.profile.screen.about_app

import android.os.Bundle
import android.view.View
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
import com.bunbeauty.papakarlo.common.ui.screen.bottom_sheet.FoodDeliveryBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.extensions.setContentWithTheme

class AboutAppBottomSheet : ComposeBottomSheet<Any>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setContentWithTheme {
            AboutAppScreen()
        }
    }
}

@Composable
private fun AboutAppScreen() {
    FoodDeliveryBottomSheet(titleStringId = R.string.title_about_app) {
        StartIconCard(
            label = stringResource(R.string.msg_about_app_developer),
            iconId = R.drawable.ic_bb,
            clickable = false,
            elevated = false,
            iconTint = FoodDeliveryTheme.colors.bunBeautyBrandColor,
        )
        StartIconCard(
            modifier = Modifier.padding(top = 8.dp),
            label = stringResource(R.string.msg_about_app_version) + BuildConfig.VERSION_NAME,
            iconId = R.drawable.ic_version,
            clickable = false,
            elevated = false,
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
