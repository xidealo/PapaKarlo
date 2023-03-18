package com.bunbeauty.papakarlo.feature.profile.screen.about_app

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.BuildConfig
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.Title
import com.bunbeauty.papakarlo.common.ui.element.card.StartIconCard
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.view_model.EmptyViewModel
import com.bunbeauty.papakarlo.databinding.BottomSheetAboutAppBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutAppBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_about_app) {

    override val viewModel: EmptyViewModel by viewModel()
    override val viewBinding by viewBinding(BottomSheetAboutAppBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.bottomSheetAboutAppCvMain.setContentWithTheme {
            AboutAppScreen()
        }
    }

    @Composable
    private fun AboutAppScreen() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .padding(bottom = 16.dp)
                .padding(horizontal = 16.dp)
        ) {
            Bang()
            Title(
                modifier = Modifier.padding(top = 16.dp),
                textStringId = R.string.title_about_app
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = FoodDeliveryTheme.dimensions.mediumSpace)
            ) {
                StartIconCard(
                    enabled = false,
                    elevated = false,
                    iconId = R.drawable.ic_bb,
                    iconTint = FoodDeliveryTheme.colors.bunBeautyBrandColor,
                    labelStringId = R.string.msg_about_app_developer
                )
                StartIconCard(
                    modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                    enabled = false,
                    elevated = false,
                    iconId = R.drawable.ic_version,
                    label = resourcesProvider.getString(R.string.msg_about_app_version) + BuildConfig.VERSION_NAME
                )
            }
        }
    }

    @Preview
    @Composable
    private fun AboutAppScreenPreview() {
        AboutAppScreen()
    }
}
