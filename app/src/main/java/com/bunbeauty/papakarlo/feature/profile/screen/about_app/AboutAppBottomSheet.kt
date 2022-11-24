package com.bunbeauty.papakarlo.feature.profile.screen.about_app

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.BuildConfig
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.Title
import com.bunbeauty.papakarlo.common.ui.element.card.IconCard
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.view_model.EmptyViewModel
import com.bunbeauty.papakarlo.databinding.BottomSheetAboutAppBinding
import com.bunbeauty.papakarlo.extensions.compose
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutAppBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_about_app) {

    override val viewModel: EmptyViewModel by viewModel()
    override val viewBinding by viewBinding(BottomSheetAboutAppBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.bottomSheetAboutAppCvMain.compose {
            AboutAppScreen()
        }
    }

    @Composable
    private fun AboutAppScreen() {
        Column(modifier = Modifier.fillMaxWidth()) {
            Title(textStringId = R.string.title_about_app)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace)
            ) {
                IconCard(
                    iconId = R.drawable.ic_bb_logo,
                    iconDescriptionStringId = R.string.description_about_app_developer,
                    iconColor = FoodDeliveryTheme.colors.bunBeautyBrandColor,
                    labelStringId = R.string.msg_about_app_developer,
                    isClickable = false
                )
                IconCard(
                    modifier = Modifier.padding(
                        top = FoodDeliveryTheme.dimensions.smallSpace,
                        bottom = FoodDeliveryTheme.dimensions.mediumSpace,
                    ),
                    iconId = R.drawable.ic_version,
                    iconDescriptionStringId = R.string.description_about_app_developer,
                    label = resourcesProvider.getString(R.string.msg_about_app_version) + BuildConfig.VERSION_NAME,
                    isClickable = false
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
